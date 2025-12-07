package com.SuperfumeKotlin.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.SuperfumeKotlin.util.Constants

/**
 * Entidad que representa un perfume en la base de datos
 * 
 * @property id Identificador único del perfume (auto-generado)
 * @property name Nombre del perfume
 * @property brand Marca del perfume
 * @property price Precio del perfume en dólares
 * @property description Descripción detallada del perfume
 * @property imageUri URI de la imagen del perfume (opcional)
 * @property category Categoría del perfume (Frescos, Florales, etc.)
 * @property size Tamaño del perfume (30ml, 50ml, etc.)
 * @property gender Género objetivo (Masculino, Femenino, Unisex)
 * @property isAvailable Indica si el perfume está disponible para venta
 * @property stock Cantidad disponible en inventario
 */
@Entity(tableName = Constants.TABLE_PERFUMES)
data class Perfume(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val brand: String,
    val price: Int,
    val description: String,
    val imageUri: String? = null,
    val category: String,
    val size: String,
    val gender: String, // "Masculino", "Femenino", "Unisex"
    val isAvailable: Boolean = true,
    val stock: Int = 0
)
