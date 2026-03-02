package me.unidok.jjvm

import me.unidok.jjvm.model.JJVMConfig
import me.unidok.jjvm.nativeclass.*
import me.unidok.jjvm.operation.*
import me.unidok.jjvm.util.JustOperation
import me.unidok.jjvm.util.Translator
import me.unidok.jjvm.util.toDebugString
import me.unidok.justcode.Handlers
import me.unidok.justcode.trigger.*
import me.unidok.justcode.value.parameter.*
import me.unidok.justcode.value.*
import org.objectweb.asm.ClassReader
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import org.objectweb.asm.tree.*
import java.util.jar.JarFile


/**
 * Сильное связывание:
 * считается, что наш jar больше ничем не расширяется и нигде не используется;
 * благодаря этому мы можем:
 * девиртуализировать вызов методов,
 * сделать экземпляр списком, а не словарём (индексируем поля),
 * встраивать код конструкторов,
 * встраивать короткие методы,
 * оптимизировать алгоритм instanceof
 *
 * Слабое связывание:
 * считается, что наш jar может быть использован другими jar,
 * поэтому он должен иметь более строгую структуру с переиспользованием;
 * из-за этого всё выше перечисленное не будет работать.
 *
 *
 *
 * Пре-обработка:
 * Создание массива операций и индексация меток
 * Узнавание всех своих классов и т.д.
 * TODO Инлайн переменные (класс Variable, Player)
 *
 * Обработка:
 * Генерация кода(?) и ветвлений
 *
 */

class JarTranslator(
    val jarPath: String,
    val config: JJVMConfig
) {
    private val finalTypes = HashSet<String>()
    private val handlers = ArrayList<Trigger>()
    private val onInit = ArrayList<JustOperation>()

    private val classes = ArrayList<ClassNode>()

    fun translate(): Handlers = JarFile(jarPath).use { jarFile ->
        var options = ClassReader.SKIP_FRAMES
        if (!config.sourceLineNumbers) options += ClassReader.SKIP_DEBUG
        for (entry in jarFile.entries()) {
            if (entry.name.endsWith(".class")) {
                val clazz = ClassNode()
                ClassReader(jarFile.getInputStream(entry)).accept(clazz, options)
                val access = clazz.access
                if (access and Opcodes.ACC_FINAL != 0) {
                    finalTypes.add(clazz.name)
                }
                classes.add(clazz)
            }
        }
        for (clazz in classes) {
            analyzeBytecode(clazz)
        }
        if (onInit.isNotEmpty()) {
            handlers.add(0, EventTrigger(onInit, "world_start"))
        }
        return Handlers(handlers)
    }


    private fun analyzeBytecode(clazz: ClassNode) {
        val debug = config.debug
        val className = clazz.name

        if (debug) {
            println("Source file: ${clazz.sourceFile}")
            println("Source debug: ${clazz.sourceDebug}")
            println("Visible annotations: ${clazz.visibleAnnotations?.joinToString { "${it.desc} ${it.values?.joinToString()} "}}")
            println("Invisible annotations: ${clazz.invisibleAnnotations?.joinToString { "$${it.desc} ${it.values?.joinToString()} "}}")
            println("Analyzing class: ${clazz.name} extends ${clazz.superName} implements ${clazz.interfaces.joinToString()}")
        }

        for (method in clazz.methods) {
            if (debug) {
                println("\tVisible annotations: ${method.visibleAnnotations?.joinToString { "${it.desc} ${it.values?.joinToString()} "}}")
                println("\tInvisible annotations: ${method.invisibleAnnotations?.joinToString { "$${it.desc} ${it.values?.joinToString()} "}}")
                println("\tMethod: ${method.name} ${method.desc} maxLocals=${method.maxLocals} maxStack=${method.maxStack}")
                println("\tBytecode:")
                for (inst in method.instructions) {
                    println("\t\t${inst.toDebugString()}")
                }
            }

            val source = SourceContext(this, clazz, method)
            source.index()
            val operations = source.walkAround()

            if (debug) {
                println("\tIR:")
                fun printOperations(prefix: String, operations: List<Operation>) {
                    operations.forEach {
                        when (it) {
                            is LoopBranch -> {
                                println("${prefix}LOOP")
                                printOperations("\t$prefix", it.operations)
                            }
                            is IfBranch -> {
                                println("${prefix}IF ${it.operand1} ${it.type} ${it.operand2}")
                                printOperations("\t$prefix", it.operations)
                                it.otherwise?.let { otherwise ->
                                    println("${prefix}ELSE")
                                    printOperations("\t$prefix", otherwise)
                                }
                            }
                            else -> println("$prefix$it")
                        }
                    }
                }
                printOperations("\t\t", operations)
                println()
            }

            val justOperations = TranslationContext(source).translate(operations)

            val methodName = method.name
            if (methodName == "<clinit>") {
                onInit.addAll(justOperations)
                continue
            }

            val methodAccess = method.access
            val methodDesc = method.desc
            val isStatic = methodAccess and Opcodes.ACC_STATIC != 0
            val argumentTypes = Type.getArgumentTypes(methodDesc)
            if (isStatic) {
                val eventHandler = method.invisibleAnnotations?.find { it.desc == "Ljustmc/annotation/EventHandler;" }
                if (eventHandler != null) {
                    val eventName = eventHandler.values?.get(1)
                    if (eventName == null) {
                        if (argumentTypes.size != 1) {
                            throw IllegalStateException("EventHandler is expecting one parameter")
                        }
                        val internalName = argumentTypes[0].internalName
                        val event = when {
                            internalName.startsWith("justmc/event/player") -> EventClasses.playerEvents[internalName.substring(20)]
                            internalName.startsWith("justmc/event/entity") -> EventClasses.entityEvents[internalName.substring(20)]
                            internalName.startsWith("justmc/event/world") -> EventClasses.worldEvents[internalName.substring(19)]
                            else -> throw IllegalStateException("EventHandler is expecting parameter type from justmc.event.*")
                        } ?: throw NullPointerException("Unknown event '$internalName'")
                        handlers.add(EventTrigger(justOperations, event))
                    } else {
                        handlers.add(EventTrigger(justOperations, eventName.toString()))
                    }
                    continue
                }
            }

            val parameters = ArrayList<Parameter>()
            var slot = 0
            val returnType = Type.getReturnType(methodDesc)
            if (returnType != Type.VOID_TYPE) {
                parameters.add(SingleParameter(
                    description = LocalizedText.Data(emptyMap(), TextValue(returnType.className)),
                    name = Translator.RETURN_VARIABLE_NAME,
                    valueType = ValueType.VARIABLE,
                    isRequired = false,
                    slot = slot++
                ))
            }

            var param = 0
            if (!isStatic || methodName == "<init>") {
                parameters.add(SingleParameter(
                    description = LocalizedText.Data(emptyMap(), TextValue(className)),
                    name = Translator.localName(param++),
                    valueType = ValueType.ANY,
                    isRequired = false,
                    slot = slot++
                ))
            }
            for (type in argumentTypes) {
                parameters.add(SingleParameter(
                    description = LocalizedText.Data(emptyMap(), TextValue(type.className)),
                    name = Translator.localName(param++),
                    valueType = if (type.internalName == "justmc/Variable") ValueType.VARIABLE else ValueType.ANY,
                    isRequired = false,
                    slot = slot++
                ))
            }

            val functionName = Translator.methodName(className, methodName, methodDesc)

            handlers.add(FunctionTrigger(
                justOperations,
                functionName,
                parameters,
                LocalizedText(LocalizedText.Data(emptyMap(), TextValue("${className.substringAfterLast('/')}.$methodName"))),
                LocalizedText(LocalizedText.Data(emptyMap(), TextValue(functionName)))
            ))
        }

        onInit.add(JustOperation(
            "set_variable_create_map_from_values", mapOf(
                "variable" to Translator.classInstance(className),
                "keys" to ArrayValue(), // TODO
                "values" to ArrayValue()
            ))
        )
    }
}