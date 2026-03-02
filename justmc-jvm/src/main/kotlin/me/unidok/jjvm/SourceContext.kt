package me.unidok.jjvm

import me.unidok.jjvm.model.JJVMConfig
import me.unidok.jjvm.operand.Operand
import me.unidok.jjvm.operand.SummaryOperand
import me.unidok.jjvm.operation.Operation
import me.unidok.justcode.value.Value
import me.unidok.justcode.value.Variable
import org.objectweb.asm.Label
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.AbstractInsnNode
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.JumpInsnNode
import org.objectweb.asm.tree.LabelNode
import org.objectweb.asm.tree.MethodNode

class SourceContext(
    val translator: JarTranslator,
    val clazz: ClassNode,
    val method: MethodNode
) {
    val labelsIndexes = HashMap<Label, Int>()
    val gotosIndexes = HashMap<Label, Int>()
    val translatedOperands = HashMap<SummaryOperand, Value>()
    val stack = ArrayDeque<Operand>(method.maxStack)
    private val finalTypes = HashMap<Operand, String>()
    private var temps = 0

    fun getFinalType(operand: Operand): String? = finalTypes[operand]

    fun tempVar(): Variable {
        return Variable("t${temps++}", Variable.Scope.LINE)
    }

    fun index() {
        method.instructions.forEachIndexed { index, inst ->
            when (inst.opcode) {
                -1 -> if (inst.type == AbstractInsnNode.LABEL) {
                    labelsIndexes[(inst as LabelNode).label] = index
                }
                Opcodes.GOTO -> {
                    gotosIndexes[(inst as JumpInsnNode).label.label] = index
                }
            }
        }
    }

    fun walkAround(): List<Operation> {
        val operations = ArrayList<Operation>()
        MethodWalker(this).walk(operations)
        return operations
    }
}