package com.SuperfumeKotlin.data.model.dto.request

import com.google.gson.annotations.SerializedName

/**
 * DTO para agregar item al carrito
 */
data class CarritoItemRequest(
    @SerializedName("perfumeId")
    val perfumeId: Int,
    
    @SerializedName("cantidad")
    val cantidad: Int
)
