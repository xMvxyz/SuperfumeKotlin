package com.SuperfumeKotlin.data.model.dto.response

import com.google.gson.annotations.SerializedName

/**
 * DTO para respuesta de rol
 * Coincide con RolResponseDto del backend
 */
data class RolResponse(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("nombre")
    val nombre: String
)
