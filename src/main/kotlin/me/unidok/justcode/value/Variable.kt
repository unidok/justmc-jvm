package me.unidok.justcode.value

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

data class Variable(
    val name: String,
    val scope: Scope = Scope.GAME
) : Value {
    enum class Scope { GAME, SAVE, LOCAL, LINE }

    override fun serialize(): JsonObject = JsonObject(mapOf(
        "type" to JsonPrimitive("variable"),
        "variable" to JsonPrimitive(name),
        "scope" to JsonPrimitive(scope.name.lowercase())
    ))

    override fun toString(): String = when (scope) {
        Scope.GAME -> "%var($name)"
        Scope.SAVE -> "%var_save($name)"
        Scope.LOCAL -> "%var_local($name)"
        Scope.LINE -> "%var_line($name)"
    }
}