package me.unidok.justcode.value

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

data class GameValue(
    val id: String,
    val selector: Selector? = null
) : Value {
    enum class Selector {
        CURRENT,
        DEFAULT,
        DEFAULT_ENTITY,
        KILLER,
        DAMAGER,
        VICTIM,
        SHOOTER,
        PROJECTILE,
        LAST_ENTITY
    }

    override fun serialize(): JsonObject = JsonObject(mapOf(
        "type" to JsonPrimitive("game_value"),
        "game_value" to JsonPrimitive(id),
        "selection" to JsonPrimitive(selector?.let {
            JsonObject(mapOf("type" to JsonPrimitive(it.name.lowercase())))
        }.toString())
    ))

    override fun toString(): String {
        return if (selector == null) "%val($id)"
        else "%val($id,$selector)"
    }

    companion object {
        val TIMESTAMP = GameValue("timestamp")
        val CPU_USAGE = GameValue("cpu_usage")
        val EVENT_BLOCK_LOCATION = GameValue("event_block_location")
        val EVENT_BLOCK = GameValue("event_block")
        val EVENT_BLOCK_FACE = GameValue("event_block_face")
        val EVENT_ITEM = GameValue("event_item")
        val EVENT_CHAT_MESSAGE = GameValue("event_chat_message")
        val EVENT_MESSAGE = GameValue("event_message")
        val EVENT_EQUIPMENT_SLOT = GameValue("event_equipment_slot")
        val EVENT_INTERACTION = GameValue("event_interaction")
        val URL = GameValue("url")
        val URL_RESPONSE = GameValue("url_response")
        val URL_RESPONSE_CODE = GameValue("url_response_code")
        val RESPONSE_HEADERS = GameValue("response_headers")
    }
}