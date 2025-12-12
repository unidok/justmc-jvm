package me.unidok.justcode.operation

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import me.unidok.justcode.value.Value

class Operation(
    val action: String,
    val values: Map<String, Value> = emptyMap(),
    val operations: List<Operation>? = null,
    val selection: String? = null,
    val conditional: Conditional? = null
) {
    class Conditional(
        val action: String,
        val isInverted: Boolean
    ) {
        fun serialize(): JsonObject = JsonObject(mapOf(
            "action" to JsonPrimitive(action),
            "is_inverted" to JsonPrimitive(isInverted)
        ))
    }

    fun serialize(): JsonObject = buildJsonObject {
        put("action", JsonPrimitive(action))
        put("values", JsonArray(values.map { JsonObject(mapOf(
            "name" to JsonPrimitive(it.key),
            "value" to it.value.serialize()
        )) }))
        if (selection != null) {
            put("selection", JsonObject(mapOf("type" to JsonPrimitive(selection))))
        }
        if (operations != null) {
            put("operations", JsonArray(operations.map { it.serialize() }))
        }
        if (conditional != null) {
            put("conditional", conditional.serialize())
        }
    }
}