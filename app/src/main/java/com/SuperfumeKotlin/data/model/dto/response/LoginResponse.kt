package com.SuperfumeKotlin.data.model.dto.response

import com.google.gson.annotations.SerializedName

/**
 * DTO para respuesta de login/registro
 * Coincide con LoginResponseDto del backend
 */
data class LoginResponse(
    @SerializedName("success")
    val success: Boolean,
    
    @SerializedName("mensaje")
    val mensaje: String,
    
    @SerializedName("usuario")
    val usuario: UsuarioResponse?,
    
    @SerializedName("token")
    val token: String?
)
