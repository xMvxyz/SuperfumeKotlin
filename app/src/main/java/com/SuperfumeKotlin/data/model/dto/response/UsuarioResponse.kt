package com.SuperfumeKotlin.data.model.dto.response

import com.google.gson.annotations.SerializedName

/**
 * DTO para respuesta de usuario
 * Coincide con UsuarioResponseDto del backend
 */
data class UsuarioResponse(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("nombre")
    val nombre: String,
    
    @SerializedName("correo")
    val correo: String,
    
    @SerializedName("telefono")
    val telefono: String?,
    
    @SerializedName("direccion")
    val direccion: String?,
    
    @SerializedName("rol")
    val rol: RolResponse
)
