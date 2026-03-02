package me.unidok.jjvm.operand

import me.unidok.jjvm.TranslationContext
import me.unidok.justcode.value.Value
import me.unidok.justcode.value.Variable

class OperandResult(
    val summary: SummaryOperand
) : Operand {
    override fun translate(context: TranslationContext, variable: Variable?): Value {
        return context.source.translatedOperands.getOrPut(summary) {
            summary.translate(context, variable)
        }
    }

    override fun toString(): String = "OperandResult(summary=$summary)"
}