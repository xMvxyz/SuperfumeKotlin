package com.SuperfumeKotlin.data.model.dto.response

import com.google.gson.annotations.SerializedName

/**
 * DTO para respuesta de carrito
 */
data class CarritoResponse(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("usuarioId")
    val usuarioId: Int,
    
    @SerializedName("usuarioNombre")
    val usuarioNombre: String,
    
    @SerializedName("items")
    val items: List<CarritoItemResponse>,
    
    @SerializedName("fechaCreacion")
    val fechaCreacion: String,
    
    @SerializedName("estado")
    val estado: String,
    
    @SerializedName("total")
    val total: Double
)
