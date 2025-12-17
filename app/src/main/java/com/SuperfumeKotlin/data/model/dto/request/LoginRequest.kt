package com.SuperfumeKotlin.data.model.dto.request

import com.google.gson.annotations.SerializedName

/**
 * DTO para solicitud de login
 * Coincide con LoginRequestDto del backend
 */
data class LoginRequest(
    @SerializedName("email")
    val email: String,
    
    @SerializedName("password")
    val password: String
)
