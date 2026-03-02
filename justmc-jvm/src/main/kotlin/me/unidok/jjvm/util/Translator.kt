package me.unidok.jjvm.util

import me.unidok.jjvm.TranslationContext
import me.unidok.jjvm.operand.DynamicConstant
import me.unidok.jjvm.operand.GetStatic
import me.unidok.jjvm.operand.LoadFromLocal
import me.unidok.jjvm.operand.NativeConstant
import me.unidok.jjvm.operand.Operand
import me.unidok.justcode.value.TextValue
import me.unidok.justcode.value.Value
import me.unidok.justcode.value.Variable

object Translator {
    const val RETURN_VARIABLE_NAME = "res"
    val ADDRESS_VARIABLE = Variable("ADR")
    val RETURN_VARIABLE = Variable(RETURN_VARIABLE_NAME, Variable.Scope.LINE)
    val RETURN_KEY = TextValue(RETURN_VARIABLE_NAME)
    val LINE_NUMBER_VARIABLE = Variable("ln", Variable.Scope.LOCAL)

    fun methodName(owner: String, name: String, desc: String): String = "$owner.$name$desc"
    fun static(owner: String, name: String): Variable = Variable("$owner.$name")
    fun argumentKey(n: Int): TextValue = TextValue(localName(n))
    fun localName(n: Int): String = "#$n"
    fun local(n: Int): Variable = Variable(localName(n), Variable.Scope.LINE)
    fun instance(address: Value): Variable = instance(address.toString())
    fun classInstance(a: Value): Variable = classInstance("%entry($a,class)")
    fun classInstance(className: String): Variable = instance("Class<$className>")
    fun instance(address: String): Variable = Variable(address)

    fun setVariable(variable: Variable, value: Value): JustOperation = JustOperation(
        "set_variable_value", mapOf(
            "variable" to variable,
            "value" to value
        )
    )

    fun setVariable(context: TranslationContext, variable: Variable?, value: Operand): Value {
        if (value is DynamicConstant || value is LoadFromLocal || value is GetStatic || value is NativeConstant) {
            val value = value.translate(context, null)
            if (variable != null && variable != value) {
                context.addOperation(setVariable(variable, value))
                return variable
            }
            return value
        }
        return value.translate(context, variable)
    }

    fun newInstance(context: TranslationContext, variable: Variable?): Variable {
        val variable = variable ?: context.tempVar()
        context.addOperation(
            JustOperation(
                "set_variable_increment", mapOf(
                    "variable" to ADDRESS_VARIABLE
                )
            )
        )
        context.addOperation(setVariable(variable, ADDRESS_VARIABLE))
        return variable
    }
}