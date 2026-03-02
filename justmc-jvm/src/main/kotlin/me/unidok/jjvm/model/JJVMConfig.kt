package me.unidok.jjvm.model

import kotlinx.serialization.Serializable

@Serializable
data class JJVMConfig(
    val isModule: Boolean = false,
    val sourceLineNumbers: Boolean = false,
    val exceptionStackTrace: Boolean = false,
    val debug: Boolean = false,
    val prettyOutput: Boolean = false,
)