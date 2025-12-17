package com.SuperfumeKotlin.data.model.dto.response

import com.google.gson.annotations.SerializedName

/**
 * DTO para respuesta de pago
 */
data class PagoResponse(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("pedidoId")
    val pedidoId: Int,
    
    @SerializedName("monto")
    val monto: Double,
    
    @SerializedName("metodoPago")
    val metodoPago: String,
    
    @SerializedName("estado")
    val estado: String,
    
    @SerializedName("fechaPago")
    val fechaPago: String,
    
    @SerializedName("transaccionId")
    val transaccionId: String?
)
