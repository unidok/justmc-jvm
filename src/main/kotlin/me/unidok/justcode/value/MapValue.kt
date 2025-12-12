package me.unidok.justcode.value

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

class MapValue(
    val map: Map<Value, Value>
) : Value {
    override fun serialize(): JsonObject = JsonObject(mapOf(
        "type" to JsonPrimitive("map"),
        "values" to JsonObject(map.entries.associate {
            it.key.serialize().toString() to it.value.serialize()
        })
    ))
}