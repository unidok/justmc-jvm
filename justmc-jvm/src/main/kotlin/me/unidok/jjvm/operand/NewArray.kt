package me.unidok.jjvm.operand

import me.unidok.jjvm.util.JustOperation
import me.unidok.jjvm.TranslationContext
import me.unidok.jjvm.util.Translator
import me.unidok.justcode.value.NumberValue
import me.unidok.justcode.value.Value
import me.unidok.justcode.value.Variable

class NewArray(
    @JvmField val size: Operand
) : SummaryOperand {
    override fun translate(context: TranslationContext, variable: Variable?): Value {
        val variable = Translator.newInstance(context, variable)
        val size = size.translate(context, null)
        if (false && size is NumberValue) {
//            val values = MutableList<Value>(size.number.toInt()) { NumberValue.CONST_0 }
//            var next = context.next()
//            while (next != null) {
//                if (next !is StoreToArray) continue
//                if (next.array != this) continue
//                val index = next.index
//                if (index !is DynamicConstant) continue
//                val indexValue = index.value
//                if (indexValue !is NumberValue) continue
//                values[indexValue.number.toInt()] = next.value.translate(context, null)
//                next = context.next()
//            }
//            context.addOperation(JustOperation(
//                "set_variable_create_list", mapOf(
//                    "variable" to Translator.instance(variable),
//                    "values" to ArrayValue(values)
//                ))
//            )
        } else {
            context.addOperation(JustOperation(
                "set_variable_create_list", mapOf(
                    "variable" to Translator.instance(variable),
                ))
            )
            context.addOperation(JustOperation(
                "repeat_multi_times", mapOf(
                    "amount" to size
                ), listOf(
                    JustOperation(
                        "set_variable_append_value", mapOf(
                            "variable" to Translator.instance(variable),
                            "values" to DynamicConstant.CONST_0.value
                        )
                    )
                ))
            )
        }
        return variable
    }

    override fun toString(): String = "NewArray(size=$size)"
}