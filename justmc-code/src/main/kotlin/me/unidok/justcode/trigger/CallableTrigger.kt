package me.unidok.justcode.trigger

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import me.unidok.justcode.operation.Operation
import me.unidok.justcode.value.ArrayValue
import me.unidok.justcode.value.ItemValue
import me.unidok.justcode.value.LocalizedText
import me.unidok.justcode.value.Value
import me.unidok.justcode.value.parameter.Parameter

abstract class CallableTrigger(
    type: Type,
    operations: List<Operation>,
    val name: String,
    val parameters: MutableList<Parameter> = mutableListOf(),
    val displayName: LocalizedText? = null,
    val displayDescription: LocalizedText? = null,
    val isHidden: Boolean = false,
    val icon: ItemValue? = null,
) : Trigger(type, operations) {
    override fun toMap(position: Int): MutableMap<String, JsonElement> =
        super.toMap(position).apply {
            put("name", JsonPrimitive(name))
            put("values", JsonArray(buildList {
                add(Value.named("parameters", ArrayValue(parameters.toMutableList())))
                if (displayName != null) {
                    add(Value.named("display_name", displayName))
                }
                if (displayDescription != null) {
                    add(Value.named("display_description", displayDescription))
                }
                if (isHidden) {
                    add(Value.named("is_hidden", JsonPrimitive(true)))
                }
                if (icon != null) {
                    add(Value.named("icon", icon))
                }
            }))
        }
}