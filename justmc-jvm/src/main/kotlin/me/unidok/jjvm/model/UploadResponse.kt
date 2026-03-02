package me.unidok.jjvm.model

import kotlinx.serialization.Serializable

@Serializable
data class UploadResponse(
    val id: String? = null,
    val error: String? = null
)