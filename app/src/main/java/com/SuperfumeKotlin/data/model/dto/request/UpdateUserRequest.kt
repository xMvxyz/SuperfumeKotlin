package com.SuperfumeKotlin.data.model.dto.request

import com.google.gson.annotations.SerializedName

/**
 * DTO para actualizar información de usuario
 */
data class UpdateUserRequest(
    @SerializedName("nombre")
    val nombre: String,
    
    @SerializedName("correo")
    val correo: String,
    
    @SerializedName("telefono")
    val telefono: String?,
    
    @SerializedName("direccion")
    val direccion: String?,
    
    @SerializedName("rut")
    val rut: String?
)
