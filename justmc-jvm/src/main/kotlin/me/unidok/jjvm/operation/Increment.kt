package me.unidok.jjvm.operation

import me.unidok.jjvm.util.JustOperation
import me.unidok.jjvm.TranslationContext
import me.unidok.jjvm.util.Translator
import me.unidok.justcode.value.NumberValue

class Increment(
    @JvmField val local: Int,
    @JvmField val value: Int
) : Operation {
    override fun translate(context: TranslationContext) {
        val variable = Translator.local(local)
        val value = value
        context.addOperation(when {
            value == 1 -> JustOperation(
                "set_variable_increment", mapOf(
                    "variable" to variable
                )
            )
            value == -1 -> JustOperation(
                "set_variable_decrement", mapOf(
                    "variable" to variable
                )
            )
            value >= 0 -> JustOperation(
                "set_variable_increment", mapOf(
                    "variable" to variable,
                    "value" to NumberValue(value.toDouble())
                )
            )
            else -> JustOperation(
                "set_variable_decrement", mapOf(
                    "variable" to variable,
                    "value" to NumberValue(value.toDouble())
                )
            )
        })
    }

    override fun toString(): String = "Increment(local=$local, value=$value)"
}