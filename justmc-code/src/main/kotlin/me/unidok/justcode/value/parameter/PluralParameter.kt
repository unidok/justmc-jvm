package me.unidok.justcode.value.parameter

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import me.unidok.justcode.value.LocalizedText
import me.unidok.justcode.value.Value
import me.unidok.justcode.value.ValueType

class PluralParameter(
    description: LocalizedText.Data,
    name: String,
    val valueType: ValueType,
    val isRequired: Boolean,
    val ignoreEmptyValues: Boolean,
    val defaultValue: Value,
    val slots: List<Int>,
    val descriptionSlots: List<Int>
) : Parameter(Type.PLURAL, description, name) {
    override fun toMap(): MutableMap<String, JsonElement> {
        return super.toMap().apply {
            put("value_type", JsonPrimitive(valueType.toString()))
            put("is_required", JsonPrimitive(isRequired.toString()))
            put("ignore_empty_values", JsonPrimitive(ignoreEmptyValues.toString()))
            put("default_value", JsonPrimitive(defaultValue.serialize().toString()))
            put("slots", JsonPrimitive(JsonArray(slots.map { JsonPrimitive(it) }).toString()))
            put("description_slots", JsonPrimitive(JsonArray(descriptionSlots.map { JsonPrimitive(it) }).toString()))
        }
    }
}