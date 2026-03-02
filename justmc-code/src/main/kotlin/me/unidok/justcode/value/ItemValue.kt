package me.unidok.justcode.value

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

data class ItemValue(
    val item: String
) : Value {
    override fun serialize(): JsonObject = JsonObject(mapOf(
        "type" to JsonPrimitive("item"),
        "item" to JsonPrimitive(item)
    ))
}