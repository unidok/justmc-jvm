package me.unidok.justcode.value

import kotlinx.serialization.json.JsonObject

data object EmptyValue : Value {
    private val OBJECT = JsonObject(emptyMap())
    override fun serialize(): JsonObject = OBJECT
}