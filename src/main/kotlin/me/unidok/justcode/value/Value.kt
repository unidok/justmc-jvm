package me.unidok.justcode.value

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import org.objectweb.asm.Type

interface Value {
    fun serialize(): JsonObject

    object Empty : Value {
        private val OBJECT = JsonObject(emptyMap())
        override fun serialize(): JsonObject = OBJECT
    }

    companion object {
        fun named(name: String, value: Value) = named(name, value.serialize())

        fun named(name: String, value: JsonElement) = JsonObject(mapOf(
            "name" to JsonPrimitive(name),
            "value" to value
        ))

        fun fromAny(obj: Any): Value = when (obj) {
            is Integer -> NumberValue(obj.toDouble())
            is Float -> NumberValue(obj.toDouble())
            is Long -> NumberValue(obj.toDouble())
            is Double -> NumberValue(obj)
            is String -> TextValue(obj)
            is Type -> throw IllegalStateException("Пока что нету классов")
            else -> throw IllegalStateException("Unknown value ${obj.javaClass} $obj")
        }
    }
}