package me.unidok.justcode.value

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

interface Value {
    fun serialize(): JsonObject

    companion object {
        fun named(name: String, value: Value) = named(name, value.serialize())

        fun named(name: String, value: JsonElement) = JsonObject(mapOf(
            "name" to JsonPrimitive(name),
            "value" to value
        ))
    }
}