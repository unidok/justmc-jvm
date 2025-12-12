package me.unidok.justcode.value

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlin.math.floor

data class NumberValue(
    val number: Double
) : Value {
    constructor(number: Int) : this(number.toDouble())

    override fun serialize(): JsonObject = JsonObject(mapOf(
        "type" to JsonPrimitive("number"),
        "number" to JsonPrimitive(if (floor(number) == number) number.toLong() else number)
    ))

    override fun toString(): String {
        return number.toBigDecimal().stripTrailingZeros().toPlainString()
    }

    companion object {
        val CONST_M1 = NumberValue(-1.0)
        val CONST_0 = NumberValue(0.0)
        val CONST_1 = NumberValue(1.0)
        val CONST_2 = NumberValue(2.0)
        val CONST_3 = NumberValue(3.0)
        val CONST_4 = NumberValue(4.0)
        val CONST_5 = NumberValue(5.0)
    }
}