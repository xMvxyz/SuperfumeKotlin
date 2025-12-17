package com.SuperfumeKotlin.data.model.dto.request

import com.google.gson.annotations.SerializedName

/**
 * DTO para solicitud de registro
 * Coincide con RegisterRequestDto del backend
 */
data class RegisterRequest(
    @SerializedName("name")
    val name: String,
    
    @SerializedName("email")
    val email: String,
    
    @SerializedName("password")
    val password: String,
    
    @SerializedName("rut")
    val rut: String? = null,
    
    @SerializedName("phone")
    val phone: String? = null,
    
    @SerializedName("address")
    val address: String? = null,
    
    @SerializedName("role")
    val role: String? = "cliente"
)
