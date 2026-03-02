package me.unidok.justcode.value

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject

data class LocalizedText(
    val data: Data
) : Value {
    companion object {
        val EMPTY_DATA = Data(emptyMap(), null)
    }

    class Data(
        val translations: Map<String, TextValue>,
        val fallback: TextValue?
    ) {
        fun serialize(): JsonObject = buildJsonObject {
            put("translations", JsonObject(translations.entries.associate { (locale, translation) ->
                locale to JsonObject(mapOf(
                    "rawText" to JsonPrimitive(translation.text),
                    "parsingType" to JsonPrimitive(translation.parsing.toString())
                ))
            }))
            if (fallback != null) {
                put("fallback", JsonObject(mapOf(
                    "rawText" to JsonPrimitive(fallback.text),
                    "parsingType" to JsonPrimitive(fallback.parsing.toString())
                )))
            }
        }
    }

    override fun serialize(): JsonObject = JsonObject(mapOf(
        "type" to JsonPrimitive("localized_text"),
        "data" to JsonPrimitive(data.serialize().toString())
    ))
}