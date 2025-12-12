package me.unidok.justcode.trigger

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import me.unidok.justcode.operation.Operation

class EventTrigger(
    operations: List<Operation>,
    val name: String,
) : Trigger(Type.EVENT, operations) {
    override fun toMap(position: Int): MutableMap<String, JsonElement> =
        super.toMap(position).apply {
            put("event", JsonPrimitive(name))
        }
}