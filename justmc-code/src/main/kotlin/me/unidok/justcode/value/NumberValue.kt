package me.unidok.justcode.value

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlin.math.floor

data class NumberValue(
    val number: Double
) : Value {
    override fun serialize(): JsonObject = JsonObject(mapOf(
        "type" to JsonPrimitive("number"),
        "number" to JsonPrimitive(if (floor(number) == number) number.toLong() else number)
    ))

    override fun toString(): String {
        return number.toBigDecimal().stripTrailingZeros().toPlainString()
    }
}