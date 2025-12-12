package me.unidok.justcode.value

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

class LocationValue(
    val x: Double,
    val y: Double,
    val z: Double,
    val yaw: Double,
    val pitch: Double
) : Value {
    override fun serialize(): JsonObject = JsonObject(mapOf(
        "type" to JsonPrimitive("location"),
        "x" to JsonPrimitive(x),
        "y" to JsonPrimitive(y),
        "z" to JsonPrimitive(z),
        "yaw" to JsonPrimitive(yaw),
        "pitch" to JsonPrimitive(pitch),
    ))
}