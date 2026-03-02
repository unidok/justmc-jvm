package me.unidok.jjvm.operation

import me.unidok.jjvm.TranslationContext
import me.unidok.jjvm.util.Translator
import me.unidok.jjvm.operand.Operand

class ReturnValue(
    @JvmField val value: Operand
) : Operation {
    override fun translate(context: TranslationContext) {
        Translator.setVariable(context, Translator.RETURN_VARIABLE, value)
        // TODO
    }

    override fun toString(): String = "ReturnValue(value=$value)"
}