package me.unidok.justcode.value

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

data class ArrayValue(
    val values: MutableList<Value>
) : Value {
    constructor(vararg values: Value) : this(values.toMutableList())

    override fun serialize(): JsonObject = JsonObject(mapOf(
        "type" to JsonPrimitive("array"),
        "values" to JsonArray(values.map { it.serialize() })
    ))
}