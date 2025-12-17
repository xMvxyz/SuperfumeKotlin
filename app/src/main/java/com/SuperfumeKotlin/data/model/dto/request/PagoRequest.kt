package com.SuperfumeKotlin.data.model.dto.request

import com.google.gson.annotations.SerializedName

/**
 * DTO para crear pago
 */
data class PagoRequest(
    @SerializedName("pedidoId")
    val pedidoId: Int,
    
    @SerializedName("monto")
    val monto: Double,
    
    @SerializedName("metodoPago")
    val metodoPago: String
)
