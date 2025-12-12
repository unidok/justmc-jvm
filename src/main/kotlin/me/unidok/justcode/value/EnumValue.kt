package me.unidok.justcode.value

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

class EnumValue(
    val enum: String
) : Value {
    constructor(bool: Boolean) : this(bool.toString())

    override fun serialize(): JsonObject = JsonObject(mapOf(
        "type" to JsonPrimitive("enum"),
        "enum" to JsonPrimitive(enum)
    ))
}