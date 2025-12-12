package me.unidok.jjvm

import me.unidok.jjvm.method.MethodInvoker
import me.unidok.justcode.Handlers
import me.unidok.justcode.operation.Operation
import me.unidok.justcode.trigger.EventTrigger
import me.unidok.justcode.trigger.FunctionTrigger
import me.unidok.justcode.trigger.Trigger
import me.unidok.justcode.value.*
import me.unidok.justcode.value.parameter.Parameter
import me.unidok.justcode.value.parameter.SingleParameter
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

private fun localName(n: Int) = "#$n"
private fun local(n: Int) = Variable(localName(n), Variable.Scope.LINE)
private const val RETURN_VARIABLE_NAME = "res"
private val ADDRESS_VARIABLE = Variable("ADR")

class JarTranslator(
    val jarPath: String,
   // val executors: List<MethodInvoker>
) {
    class Context(
        val clazz: ClassNode,
        val method: MethodNode
    ) {
        val stack = ArrayDeque<Value>(method.maxStack)
        val operations = ArrayList<Operation>()
        var inst = method.instructions.first

        private var temps = 0
        fun tempVar(): Variable {
            val next = inst.next
            if (next != null) {
                val opcode = next.opcode
                if (opcode >= Opcodes.ISTORE && opcode <= Opcodes.ASTORE) {
                    inst = next
                    return local((next as VarInsnNode).`var`)
                }
                if (opcode >= Opcodes.IRETURN && opcode <= Opcodes.ARETURN) {
                    inst = next
                    return Variable(RETURN_VARIABLE_NAME, Variable.Scope.LINE)
                }
            }
            return Variable("t${temps++}", Variable.Scope.LINE)
        }
    }

    private val handlers = ArrayList<Trigger>()

    fun translate(isModule: Boolean): Handlers {
        if (!isModule) {
            handlers.addAll(Native.instructions)
        }
        val jarFile = JarFile(jarPath)
        for (entry in jarFile.entries()) {
            if (entry.name.endsWith(".class")) {
                val clazz = ClassNode()
                ClassReader(jarFile.getInputStream(entry)).accept(clazz, ClassReader.EXPAND_FRAMES)
                analyzeBytecode(clazz)
            }
        }
        jarFile.close()
        return Handlers(handlers)
    }


    private fun analyzeBytecode(clazz: ClassNode) {
        println("Analyzing class: ${clazz.name}")

//        for (field in classNode.fields) {
//            println("\tField: ${field.name} ${field.desc} ${field.value}")
//
//            if (field.access and Opcodes.ACC_STATIC != 0 && field.value != null) {
//                main.add(Operation("set_variable_value", mapOf(
//                    "variable" to Variable("${classNode.name}.${field.name}"),
//                    "value" to Value.fromAny(field.value)
//                )))
//            }
//        }

        for (method in clazz.methods) {
            println("\tMethod: ${method.name} ${method.desc} maxLocals=${method.maxLocals} maxStack=${method.maxStack}")

            val context = Context(clazz, method)
            val stack = context.stack
            val operations = context.operations

            while (true) {
                var t: Throwable? = null
                val inst = context.inst
                try {
                    val opcode = inst.opcode
                    print("\t\t$opcode\t")

                    when (opcode) {
                        -1 -> {
                            print("//")
                        }
                        Opcodes.NOP -> {
                            print("NOP")
                        }

                        // получение константы
                        Opcodes.ACONST_NULL -> {
                            print("ACONST_NULL")
                            stack.addFirst(NumberValue.CONST_0)
                        }
                        Opcodes.ICONST_M1 -> {
                            print("ICONST_M1")
                            stack.addFirst(NumberValue.CONST_M1)
                        }
                        Opcodes.ICONST_0 -> {
                            print("ICONST_0")
                            stack.addFirst(NumberValue.CONST_0)
                        }
                        Opcodes.ICONST_1 -> {
                            print("ICONST_1")
                            stack.addFirst(NumberValue.CONST_1)
                        }
                        Opcodes.ICONST_2 -> {
                            print("ICONST_2")
                            stack.addFirst(NumberValue.CONST_2)
                        }
                        Opcodes.ICONST_3 -> {
                            print("ICONST_3")
                            stack.addFirst(NumberValue.CONST_3)
                        }
                        Opcodes.ICONST_4 -> {
                            print("ICONST_4")
                            stack.addFirst(NumberValue.CONST_4)
                        }
                        Opcodes.ICONST_5 -> {
                            print("ICONST_5")
                            stack.addFirst(NumberValue.CONST_5)
                        }
                        Opcodes.LCONST_0 -> {
                            print("LCONST_0")
                            stack.addFirst(NumberValue.CONST_0)
                        }
                        Opcodes.LCONST_1 -> {
                            print("LCONST_1")
                            stack.addFirst(NumberValue.CONST_1)
                        }
                        Opcodes.FCONST_0 -> {
                            print("FCONST_0")
                            stack.addFirst(NumberValue.CONST_0)
                        }
                        Opcodes.FCONST_1 -> {
                            print("FCONST_1")
                            stack.addFirst(NumberValue.CONST_1)
                        }
                        Opcodes.FCONST_2 -> {
                            print("FCONST_2")
                            stack.addFirst(NumberValue.CONST_2)
                        }
                        Opcodes.DCONST_0 -> {
                            print("DCONST_0")
                            stack.addFirst(NumberValue.CONST_0)
                        }
                        Opcodes.DCONST_1 -> {
                            print("DCONST_1")
                            stack.addFirst(NumberValue.CONST_1)
                        }
                        Opcodes.BIPUSH -> {
                            print("BIPUSH")
                            stack.addFirst(NumberValue((context.inst as IntInsnNode).operand))
                        }
                        Opcodes.SIPUSH -> {
                            print("SIPUSH")
                            stack.addFirst(NumberValue((context.inst as IntInsnNode).operand))
                        }
                        Opcodes.LDC -> {
                            print("LDC")
                            stack.addFirst(Value.fromAny((context.inst as LdcInsnNode).cst))
                        }

                        // получение из переменной
                        in Opcodes.ILOAD..Opcodes.ALOAD -> {
                            print("LOAD")
                            stack.addFirst(local((context.inst as VarInsnNode).`var`))
                        }

                        // получение из массива
                        in Opcodes.IALOAD..Opcodes.SALOAD -> {
                            print("?ALOAD")
                            val index = stack.removeFirst()
                            val array = Variable(stack.removeFirst().toString())
                            val variable = context.tempVar()
                            operations.add(Operation("set_variable_get_list_value", mapOf(
                                "variable" to variable,
                                "list" to array,
                                "number" to index
                            )))
                            stack.addFirst(variable)
                        }

                        // запись в переменную
                        in Opcodes.ISTORE..Opcodes.ASTORE -> {
                            print("?STORE")
                            operations.add(Operation("set_variable_value", mapOf(
                                "variable" to local((context.inst as VarInsnNode).`var`),
                                "value" to stack.removeFirst()
                            )))
                        }

                        // запись в массив
                        in Opcodes.IASTORE..Opcodes.SASTORE -> {
                            print("?ASTORE")
                            val value = stack.removeFirst()
                            val index = stack.removeFirst()
                            val array = Variable(stack.removeFirst().toString())
                            operations.add(Operation("set_variable_set_list_value", mapOf(
                                "variable" to array,
                                "list" to array,
                                "number" to index,
                                "value" to value
                            )))
                        }

                        // очистка стека
                        Opcodes.POP, Opcodes.POP2 -> {
                            print("?POP")
                            stack.removeFirst()
                        }

                        // дублирование элемента в стеке
                        Opcodes.DUP, Opcodes.DUP2 -> {
                            print("?DUP")
                            stack.addFirst(stack.first())
                        }
                        Opcodes.DUP_X1, Opcodes.DUP2_X1 -> {
                            print("?DUP_X1")
                            stack.add(2, stack.first())
                        }
                        Opcodes.DUP_X2, Opcodes.DUP2_X2 -> {
                            print("?DUP_X2")
                            stack.add(3, stack.first())
                        }

                        Opcodes.SWAP -> {
                            print("SWAP")
                            stack.add(1, stack.removeFirst())
                        }

                        in Opcodes.IADD..Opcodes.DADD -> {
                            print("?ADD")
                            val variable = context.tempVar()
                            val second = stack.removeFirst()
                            val first = stack.removeFirst()
                            operations.add(Operation("set_variable_add", mapOf(
                                "variable" to variable,
                                "value" to ArrayValue(first, second)
                            )))
                            stack.addFirst(variable)
                        }
                        Opcodes.IINC -> {
                            print("IINC")
                            val inst = context.inst as IincInsnNode
                            val variable = local(inst.`var`)
                            val incr = inst.incr
                            if (incr == 1) {
                                operations.add(Operation("set_variable_increment", mapOf(
                                    "variable" to variable
                                )))
                            } else if (incr == -1) {
                                operations.add(Operation("set_variable_decrement", mapOf(
                                    "variable" to variable
                                )))
                            } else if (incr >= 0) {
                                operations.add(Operation("set_variable_increment", mapOf(
                                    "variable" to variable,
                                    "value" to NumberValue(incr)
                                )))
                            } else {
                                operations.add(Operation("set_variable_decrement", mapOf(
                                    "variable" to variable,
                                    "value" to NumberValue(incr)
                                )))
                            }
                        }
                        Opcodes.F2I, Opcodes.F2L, Opcodes.D2I, Opcodes.D2L -> {
                            print("?F2I")
                            val variable = context.tempVar()
                            operations.add(Operation("set_variable_floor", mapOf(
                                "variable" to variable,
                                "number" to stack.removeFirst()
                            )))
                            stack.addFirst(variable)
                        }
                        Opcodes.IF_ICMPGE -> {
                            print("IF_ICMPGE")
                        }
                        Opcodes.GOTO -> {
                            print("GOTO")
                        }
                        Opcodes.RET -> {
                            print("RET")
                        }
                        in Opcodes.IRETURN..Opcodes.ARETURN -> {
                            print("?RETURN")
                            operations.add(Operation("set_variable_value", mapOf(
                                "variable" to Variable(RETURN_VARIABLE_NAME, Variable.Scope.LINE),
                                "value" to stack.removeFirst()
                            )))
                            //operations.add(Operation("control_return_function"))
                        }
                        Opcodes.RETURN -> {
                            print("RETURN")
                            //operations.add(Operation("control_return_function"))
                        }
                        Opcodes.GETSTATIC -> {
                            print("GETSTATIC")
                            val inst = context.inst as FieldInsnNode
                            val owner = inst.owner
                            val name = inst.name
                            stack.addFirst(Native.fields[owner]?.get(name) ?: Variable("$owner.$name"))
                        }
                        Opcodes.PUTSTATIC -> {
                            print("PUTSTATIC")
                            val inst = context.inst as FieldInsnNode
                            operations.add(Operation("set_variable_value", mapOf(
                                "variable" to Variable("${inst.owner}.${inst.name}"),
                                "value" to stack.removeFirst()
                            )))
                        }
                        Opcodes.GETFIELD -> {
                            print("GETFIELD")
                            val variable = context.tempVar()
                            operations.add(Operation("set_variable_get_map_value", mapOf(
                                "variable" to variable,
                                "map" to Variable(stack.removeFirst().toString()),
                                "key" to TextValue((context.inst as FieldInsnNode).name)
                            )))
                            stack.addFirst(variable)
                        }
                        Opcodes.PUTFIELD -> {
                            print("PUTFIELD")
                            val value = stack.removeFirst()
                            val instance = Variable(stack.removeFirst().toString())
                            operations.add(Operation("set_variable_set_map_value", mapOf(
                                "variable" to instance,
                                "map" to instance,
                                "key" to TextValue((context.inst as FieldInsnNode).name),
                                "value" to value
                            )))
                        }
                        Opcodes.INVOKEVIRTUAL, Opcodes.INVOKESPECIAL, Opcodes.INVOKESTATIC, Opcodes.INVOKEINTERFACE -> {
                            when (opcode) {
                                Opcodes.INVOKEVIRTUAL -> print("INVOKEVIRTUAL")
                                Opcodes.INVOKESPECIAL -> print("INVOKESPECIAL")
                                Opcodes.INVOKESTATIC -> print("INVOKESTATIC")
                                Opcodes.INVOKEINTERFACE -> print("INVOKEINTERFACE")
                            }
                            val inst = context.inst as MethodInsnNode
                            val owner = inst.owner
                            val name = inst.name
                            val desc = inst.desc
                            if (Native.methods[owner]?.get(name)?.get(desc)?.invoke(context) == null) {
                                val args = buildMap<Value, Value> {
                                    var local = Type.getArgumentCount(desc)
                                    if (opcode != Opcodes.INVOKESTATIC) ++local
                                    while (--local >= 0) {
                                        put(TextValue(localName(local)), stack.removeFirst())
                                    }
                                    if (Type.getReturnType(desc) != Type.VOID_TYPE) {
                                        val result = context.tempVar()
                                        put(TextValue(RETURN_VARIABLE_NAME), result)
                                        stack.addFirst(result)
                                    }
                                }

                                val function = if (opcode == Opcodes.INVOKEVIRTUAL || opcode == Opcodes.INVOKEINTERFACE) {
                                    "%entry(${args[TextValue(localName(0))]},class).$name$desc"
                                } else {
                                    "$owner.$name$desc"
                                }

                                operations.add(Operation("call_function", mapOf(
                                    "function_name" to TextValue(function),
                                    "args" to MapValue(args)
                                )))
                            }
                        }
                        Opcodes.INVOKEDYNAMIC -> {
                            print("INVOKEDYNAMIC")
                        }
                        Opcodes.NEW -> {
                            print("NEW")

                            val address = context.tempVar()
                            operations.add(Operation("set_variable_increment", mapOf(
                                "variable" to ADDRESS_VARIABLE
                            )))
                            operations.add(Operation("set_variable_value", mapOf(
                                "variable" to address,
                                "value" to ADDRESS_VARIABLE
                            )))

                            operations.add(Operation("set_variable_create_map_from_values", mapOf(
                                "variable" to Variable("$address"),
                                "keys" to ArrayValue(TextValue("class")),
                                "values" to ArrayValue(TextValue((context.inst as TypeInsnNode).desc))
                            )))

                            stack.addFirst(address)
                        }
                        Opcodes.NEWARRAY -> {
                            print("NEWARRAY")

                            val address = context.tempVar()
                            operations.add(Operation("set_variable_increment", mapOf(
                                "variable" to ADDRESS_VARIABLE
                            )))
                            operations.add(Operation("set_variable_value", mapOf(
                                "variable" to address,
                                "value" to ADDRESS_VARIABLE
                            )))

                            val size = stack.removeFirst()
                            if (size is NumberValue) {
                                operations.add(Operation("set_variable_create_list", mapOf(
                                    "variable" to Variable("$address"),
                                    "values" to ArrayValue(List(size.number.toInt()) { NumberValue.CONST_0 })
                                )))
                            } else {
                                operations.add(Operation("set_variable_create_list", mapOf(
                                    "variable" to Variable("$address"),
                                )))
                                operations.add(Operation("repeat_times", mapOf(
                                    "times" to size
                                ), listOf(
                                    Operation("set_variable_append_value", mapOf(
                                        "variable" to Variable("$address"),
                                        "values" to NumberValue.CONST_0
                                    ))
                                )))
                            }
                        }
                        else -> {
                            print("???")
                        }
                    }
                } catch (th: Throwable) {
                    t = th
                }

                print("\t")

                when (inst) {
                    is InsnNode -> println("")
                    is IntInsnNode -> println("${inst.operand}")
                    is VarInsnNode -> println("#${inst.`var`}")
                    is TypeInsnNode -> println("${inst.desc}")
                    is FieldInsnNode -> println("${inst.owner}\t${inst.name}\t${inst.desc}")
                    is MethodInsnNode -> println("${inst.owner}\t${inst.name}\t${inst.desc}\t${inst.itf}")
                    is InvokeDynamicInsnNode -> println("invoke dynamic: desc: '${inst.desc}'; name: '${inst.name}'; bsm: '${inst.bsm}'; bsm name: '${inst.bsm.name}'; bsm desc: '${inst.bsm.desc}'; bsm owher: '${inst.bsm.owner}'; bsm tag: '${inst.bsm.tag}'; bsm interface: '${inst.bsm.isInterface}'; bsmArgs: '${inst.bsmArgs}'")
                    is JumpInsnNode -> println("jump: ${inst.label.label}")
                    is LabelNode -> println("lable: ${inst.label}")
                    is LdcInsnNode -> println("${inst.cst}")
                    is IincInsnNode -> println("#${inst.`var`} += ${inst.incr}")
                    is TableSwitchInsnNode -> println("table switch: min: ${inst.min}; max: ${inst.max}; label info: '${inst.dflt.label}' labels: '${inst.labels.joinToString {it.label.toString()}}'")
                    is LookupSwitchInsnNode -> println("lookup switch: keys: '${inst.keys.joinToString()}', dflt: '${inst.dflt.label}'; labels: '${inst.labels.joinToString {it.label.toString()}}'")
                    is MultiANewArrayInsnNode -> println("multi a new array: dims: ${inst.dims}; desc: '${inst.desc}'")
                    is FrameNode -> println("frame: type: ${inst.type}; local: '${inst.local.joinToString()}'; stack: '${inst.stack.joinToString()}'")
                    is LineNumberNode -> println("line number: ${inst.line} start: '${inst.start.label}'")
                }

                if (t != null) {
                    println("\n[!] $t\n")
                }

                context.inst = context.inst.next ?: break
            }

            val access = method.access
            val isStatic = access and Opcodes.ACC_STATIC != 0
            val argumentTypes = Type.getArgumentTypes(method.desc)
            if (isStatic && argumentTypes.size == 1) {
                val internalName = argumentTypes[0].internalName
                if (internalName.startsWith("justmc/event")) {
                    handlers.add(EventTrigger(
                        operations,
                        Native.eventMapping[internalName.substringAfterLast('/')]
                            ?: throw NullPointerException("Unknown event '$internalName'")
                    ))
                    continue
                }
            }

            val parameters = ArrayList<Parameter>()
            var slot = 0
            val returnType = Type.getReturnType(method.desc)
            if (returnType != Type.VOID_TYPE) {
                parameters.add(SingleParameter(
                    description = LocalizedText.Data(emptyMap(), TextValue(returnType.className)),
                    name = RETURN_VARIABLE_NAME,
                    valueType = ValueType.VARIABLE,
                    isRequired = true,
                    slot = slot++
                ))
            }
            var param = 0
            if (!isStatic || method.name == "<init>") {
                parameters.add(SingleParameter(
                    description = LocalizedText.Data(emptyMap(), TextValue(clazz.name)),
                    name = localName(param++),
                    valueType = ValueType.ANY,
                    isRequired = true,
                    slot = slot++
                ))
            }
            for (type in argumentTypes) {
                parameters.add(SingleParameter(
                    description = LocalizedText.Data(emptyMap(), TextValue(type.className)),
                    name = localName(param++),
                    valueType = ValueType.ANY,
                    isRequired = true,
                    slot = slot++
                ))
            }

            val functionName = "${clazz.name}.${method.name}${method.desc}"

            handlers.add(FunctionTrigger(
                operations,
                functionName,
                parameters,
                LocalizedText(LocalizedText.Data(emptyMap(), TextValue("${clazz.name.substringAfterLast('/')}.${method.name}"))),
                LocalizedText(LocalizedText.Data(emptyMap(), TextValue(functionName)))
            ))
        }
    }
}