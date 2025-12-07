package com.SuperfumeKotlin.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.SuperfumeKotlin.util.Constants

/**
 * Entidad que representa un elemento del carrito de compras
 * 
 * @property id Identificador único del elemento (auto-generado)
 * @property userId ID del usuario propietario del carrito
 * @property perfumeId ID del perfume agregado al carrito
 * @property quantity Cantidad del perfume en el carrito
 * @property addedAt Timestamp de cuando se agregó al carrito
 */
@Entity(tableName = Constants.TABLE_CART)
data class ElementoCarrito(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: Long,
    val perfumeId: Long,
    val quantity: Int,
    val addedAt: Long
)
