package com.SuperfumeKotlin.data.model.dto.response

import com.google.gson.annotations.SerializedName

/**
 * DTO para respuesta de perfume
 * Coincide con PerfumeResponseDto del backend
 */
data class PerfumeResponse(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("nombre")
    val nombre: String,
    
    @SerializedName("marca")
    val marca: String,
    
    @SerializedName("precio")
    val precio: Double,
    
    @SerializedName("stock")
    val stock: Int,
    
    @SerializedName("descripcion")
    val descripcion: String?,
    
    @SerializedName("imagenUrl")
    val imagenUrl: String?,
    
    @SerializedName("genero")
    val genero: String?,
    
    @SerializedName("fragancia")
    val fragancia: String?,
    
    @SerializedName("notas")
    val notas: String?,
    
    @SerializedName("perfil")
    val perfil: String?
)
