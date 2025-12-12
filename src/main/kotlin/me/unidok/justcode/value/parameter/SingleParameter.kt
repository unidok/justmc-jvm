package me.unidok.justcode.value.parameter

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import me.unidok.justcode.value.LocalizedText
import me.unidok.justcode.value.Value
import me.unidok.justcode.value.ValueType

class SingleParameter(
    description: LocalizedText.Data,
    name: String,
    val valueType: ValueType,
    val isRequired: Boolean,
    val defaultValue: Value = Value.Empty,
    val slot: Int,
    val descriptionSlot: Int = slot + 9
) : Parameter(Type.SINGULAR, description, name) {
    override fun toMap(): MutableMap<String, JsonElement> {
        return super.toMap().apply {
            put("value_type", JsonPrimitive(valueType.toString()))
            put("is_required", JsonPrimitive(isRequired.toString()))
            put("default_value", JsonPrimitive(defaultValue.serialize().toString()))
            put("slot", JsonPrimitive(slot))
            put("description_slot", JsonPrimitive(descriptionSlot))
        }
    }
}