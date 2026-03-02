package me.unidok.jjvm.operand

import me.unidok.jjvm.TranslationContext
import me.unidok.justcode.value.Value
import me.unidok.justcode.value.Variable

interface SummaryOperand {
    fun translate(context: TranslationContext, variable: Variable?): Value
}