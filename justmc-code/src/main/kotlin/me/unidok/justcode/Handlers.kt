package me.unidok.justcode

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import me.unidok.justcode.trigger.Trigger

class Handlers(
    val handlers: List<Trigger>
) {
    fun serialize(): JsonObject = JsonObject(mapOf(
        "handlers" to JsonArray(buildList {
            var position = 0
            for (handler in handlers) {
                add(handler.serialize(position))
                ++position
            }
        })
    ))
}