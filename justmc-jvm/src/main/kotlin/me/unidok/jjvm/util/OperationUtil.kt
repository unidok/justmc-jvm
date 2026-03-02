package me.unidok.jjvm.util

import me.unidok.jjvm.SourceContext
import me.unidok.jjvm.TranslationContext
import me.unidok.jjvm.operand.InvokeMethod
import me.unidok.jjvm.operand.NativeConstant
import me.unidok.jjvm.operand.Operand
import me.unidok.jjvm.operation.NonAffectOperation
import me.unidok.jjvm.operation.Operation
import me.unidok.jjvm.operation.TranslatableFutureOperation
import me.unidok.justcode.value.TextValue

data class NativeMethodContext(
    val method: InvokeMethod,
    val source: SourceContext,
    val operations: MutableList<Operation>
) {
    fun arg(index: Int): Operand = method.args[index]

    val self get() = method.self

    fun translateFuture(block: TranslationContext.() -> Unit) {
        operations.add(TranslatableFutureOperation(block))
    }

    fun addOperation(operation: JustOperation) {
        operations.add(NonAffectOperation(operation))
    }

    fun addOperation(operation: Operation) {
        operations.add(operation)
    }
}

typealias NativeMethod = NativeMethodContext.() -> Operand?
typealias MethodsMap = Map<String, NativeMethod>
typealias JustOperation = me.unidok.justcode.operation.Operation

fun Operand.asString(context: TranslationContext): String {
    if (this is NativeConstant) return value
    val value = translate(context, null)
    if (value !is TextValue) throw IllegalArgumentException("${value.javaClass} (from $javaClass) cannot be represented as constant string")
    return value.text
}