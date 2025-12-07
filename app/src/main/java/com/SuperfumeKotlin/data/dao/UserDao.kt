package com.SuperfumeKotlin.data.dao

import androidx.room.*
import com.SuperfumeKotlin.data.model.Usuario
import com.SuperfumeKotlin.util.Constantes
import kotlinx.coroutines.flow.Flow

/**
 * DAO para operaciones de usuarios en la base de datos
 * Maneja todas las operaciones CRUD relacionadas con usuarios
 */
@Dao
interface DaoUsuario {
    
    /**
     * Autentica un usuario con email y contraseña
     * @param email Email del usuario
     * @param contraseña Contraseña del usuario
     * @return Usuario autenticado o null si las credenciales son inválidas
     */
    @Query("SELECT * FROM ${Constantes.TABLA_USUARIOS} WHERE email = :email AND password = :contraseña")
    suspend fun autenticarUsuario(email: String, contraseña: String): Usuario?
    
    /**
     * Obtiene un usuario por su email
     * @param email Email del usuario
     * @return Usuario encontrado o null
     */
    @Query("SELECT * FROM ${Constantes.TABLA_USUARIOS} WHERE email = :email")
    suspend fun obtenerUsuarioPorEmail(email: String): Usuario?
    
    /**
     * Obtiene un usuario por su ID
     * @param id ID del usuario
     * @return Usuario encontrado o null
     */
    @Query("SELECT * FROM ${Constantes.TABLA_USUARIOS} WHERE id = :id")
    suspend fun obtenerUsuarioPorId(id: Long): Usuario?
    
    /**
     * Inserta un nuevo usuario
     * @param usuario Usuario a insertar
     * @return ID del usuario insertado
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarUsuario(usuario: Usuario): Long
    
    /**
     * Actualiza un usuario existente
     * @param usuario Usuario a actualizar
     */
    @Update
    suspend fun actualizarUsuario(usuario: Usuario)
    
    /**
     * Elimina un usuario
     * @param usuario Usuario a eliminar
     */
    @Delete
    suspend fun eliminarUsuario(usuario: Usuario)
}
