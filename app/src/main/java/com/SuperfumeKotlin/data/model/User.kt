package com.SuperfumeKotlin.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.SuperfumeKotlin.util.Constantes

/**
 * Entidad que representa un usuario en la base de datos
 * 
 * @property id Identificador único del usuario (auto-generado)
 * @property email Email del usuario (usado para login)
 * @property password Contraseña del usuario (encriptada)
 * @property firstName Nombre del usuario
 * @property lastName Apellido del usuario
 * @property phone Número de teléfono (opcional)
 * @property address Dirección del usuario (opcional)
 * @property profileImageUri URI de la imagen de perfil (opcional)
 */
@Entity(tableName = Constantes.TABLA_USUARIOS)
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val phone: String? = null,
    val address: String? = null,
    val profileImageUri: String? = null
)
