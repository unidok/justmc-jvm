package me.unidok.justcode.value

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

data class TextValue(
    val text: String,
    val parsing: Parsing = Parsing.PLAIN
) : Value {
    enum class Parsing { PLAIN, LEGACY, MINIMESSAGE, JSON }

    override fun serialize(): JsonObject = JsonObject(mapOf(
        "type" to JsonPrimitive("text"),
        "text" to JsonPrimitive(text),
        "parsing" to JsonPrimitive(parsing.name.lowercase())
    ))

    override fun toString(): String {
        return text
    }
}