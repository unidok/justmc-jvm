package me.unidok.jjvm.instruction

import me.unidok.jjvm.JarTranslator
import org.objectweb.asm.tree.AbstractInsnNode

interface InstructionExecutor {
    fun execute(inst: AbstractInsnNode, context: JarTranslator.Context): Boolean
}