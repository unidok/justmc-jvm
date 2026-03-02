package me.unidok.justcode.value

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

data class StringNumberValue(
    val number: String
) : Value {
    override fun serialize(): JsonObject = JsonObject(mapOf(
        "type" to JsonPrimitive("number"),
        "number" to JsonPrimitive(number)
    ))

    override fun toString(): String {
        return number.toBigDecimal().stripTrailingZeros().toPlainString()
    }
}