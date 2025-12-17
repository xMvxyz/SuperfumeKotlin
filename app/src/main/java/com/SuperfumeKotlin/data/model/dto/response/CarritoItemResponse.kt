package com.SuperfumeKotlin.data.model.dto.response

import com.google.gson.annotations.SerializedName

/**
 * DTO para item del carrito
 */
data class CarritoItemResponse(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("perfumeId")
    val perfumeId: Int,
    
    @SerializedName("perfumeNombre")
    val perfumeNombre: String,
    
    @SerializedName("cantidad")
    val cantidad: Int,
    
    @SerializedName("precioUnitario")
    val precioUnitario: Double,
    
    @SerializedName("subtotal")
    val subtotal: Double
)
