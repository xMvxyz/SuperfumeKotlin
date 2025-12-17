package com.SuperfumeKotlin.data.model.dto.response

import com.google.gson.annotations.SerializedName

/**
 * DTO para respuesta de pedido
 */
data class PedidoResponse(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("usuario")
    val usuario: UsuarioResponse,
    
    @SerializedName("carrito")
    val carrito: CarritoResponse,
    
    @SerializedName("total")
    val total: Double,
    
    @SerializedName("fechaPedido")
    val fechaPedido: String,
    
    @SerializedName("estado")
    val estado: String
)
