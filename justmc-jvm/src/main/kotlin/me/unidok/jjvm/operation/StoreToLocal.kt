package me.unidok.jjvm.operation

import me.unidok.jjvm.TranslationContext
import me.unidok.jjvm.util.Translator
import me.unidok.jjvm.operand.Operand

class StoreToLocal(
    @JvmField val local: Int,
    @JvmField val value: Operand
) : Operation {
    override fun translate(context: TranslationContext) {
        Translator.setVariable(context, Translator.local(local), value)
    }

    override fun toString(): String = "StoreToLocal(local=$local, value=$value)"
}