package me.unidok.justcode.trigger

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import me.unidok.justcode.operation.Operation

abstract class Trigger(
    val type: Type,
    val operations: List<Operation>
) {
    enum class Type {
        EVENT,
        FUNCTION,
        PROCESS;

        override fun toString(): String = name.lowercase()
    }

    open fun toMap(position: Int): MutableMap<String, JsonElement> = mutableMapOf(
        "type" to JsonPrimitive(type.toString()),
        "position" to JsonPrimitive(position),
        "operations" to JsonArray(operations.map { it.serialize() })
    )

    fun serialize(position: Int): JsonObject = JsonObject(toMap(position))
}