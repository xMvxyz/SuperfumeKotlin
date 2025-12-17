package com.SuperfumeKotlin.data.model.dto.request

import com.google.gson.annotations.SerializedName

/**
 * DTO para crear pedido
 */
data class PedidoRequest(
    @SerializedName("carritoId")
    val carritoId: Int
)
