package me.unidok.justcode.value.parameter

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import me.unidok.justcode.value.LocalizedText
import me.unidok.justcode.value.Value

abstract class Parameter(
    val typeKey: Type,
    val description: LocalizedText.Data,
    val name: String,
) : Value {
    enum class Type {
        SINGULAR,
        PLURAL,
        ENUM;

        override fun toString(): String = name.lowercase()
    }

    open fun toMap(): MutableMap<String, JsonElement> = mutableMapOf(
        "type" to JsonPrimitive("parameter"),
        "type_key" to JsonPrimitive(typeKey.toString()),
        "description" to JsonPrimitive(description.serialize().toString()),
        "name" to JsonPrimitive(name)
    )

    override fun serialize(): JsonObject = JsonObject(toMap())
}