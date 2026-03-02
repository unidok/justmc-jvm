package me.unidok.jjvm.operand

import me.unidok.jjvm.TranslationContext
import me.unidok.justcode.value.Value
import me.unidok.justcode.value.Variable

interface Operand {
    fun translate(context: TranslationContext, variable: Variable?): Value
}