package com.SuperfumeKotlin.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.SuperfumeKotlin.util.Constants

/**
 * Entidad que representa un perfume en la base de datos
 * Sincronizado con PerfumeModel del backend
 * 
 * @property id Identificador único del perfume (auto-generado)
 * @property name Nombre del perfume
 * @property brand Marca del perfume
 * @property price Precio del perfume (en pesos chilenos)
 * @property description Descripción detallada del perfume
 * @property imageUri URI de la imagen del perfume
 * @property gender Género objetivo (Masculino, Femenino, Unisex)
 * @property fragancia Tipo de fragancia (Frescos, Florales, Orientales, Amaderados, Cítricos)
 * @property notas Notas del perfume
 * @property perfil Perfil aromático del perfume
 * @property stock Cantidad disponible en inventario
 * @property isAvailable Indica si el perfume está disponible para venta
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
    val gender: String, // "Masculino", "Femenino", "Unisex"
    val fragancia: String, // "Frescos", "Florales", "Orientales", "Amaderados", "Cítricos"
    val notas: String, // Notas aromáticas
    val perfil: String, // Perfil aromático
    val stock: Int = 0,
    val isAvailable: Boolean = true,
    // Campos legacy mantenidos para compatibilidad
    val category: String = fragancia, // Alias de fragancia
    val size: String = "50ml" // Tamaño por defecto
)
