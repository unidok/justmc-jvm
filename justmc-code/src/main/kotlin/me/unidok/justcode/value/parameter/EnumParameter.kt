package me.unidok.justcode.value.parameter

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import me.unidok.justcode.value.ItemValue
import me.unidok.justcode.value.LocalizedText

class EnumParameter(
    description: LocalizedText.Data,
    name: String,
    val slot: Int,
    val elements: List<Element>,
    val defaultElement: String = ""
) : Parameter(Type.ENUM, description, name) {
    class Element(
        val name: String,
        val displayName: LocalizedText.Data,
        val icon: ItemValue
    ) {
        fun serialize(): JsonObject = JsonObject(mapOf(
            "name" to JsonPrimitive(name),
            "display_name" to displayName.serialize(),
            "icon" to JsonPrimitive(icon.item)
        ))
    }

    override fun toMap(): MutableMap<String, JsonElement> {
        return super.toMap().apply {
            put("slot", JsonPrimitive(slot))
            put("elements", JsonPrimitive(JsonArray(elements.map {
                JsonPrimitive(it.serialize().toString())
            }).toString()))
            put("default_element", JsonPrimitive(defaultElement))
        }
    }
}