package com.SuperfumeKotlin.data.dao

import androidx.room.*
import com.SuperfumeKotlin.data.model.Usuario
import com.SuperfumeKotlin.util.Constants
import kotlinx.coroutines.flow.Flow


@Dao
interface DaoUsuario {
    

    @Query("SELECT * FROM ${Constants.TABLE_USERS} WHERE email = :email AND password = :contraseña")
    suspend fun autenticarUsuario(email: String, contraseña: String): Usuario?
    

    @Query("SELECT * FROM ${Constants.TABLE_USERS} WHERE email = :email")
    suspend fun obtenerUsuarioPorEmail(email: String): Usuario?
    

    @Query("SELECT * FROM ${Constants.TABLE_USERS} WHERE id = :id")
    suspend fun obtenerUsuarioPorId(id: Long): Usuario?
    
    /**
     * Inserta un nuevo usuario
     * @param usuario Usuario a insertar
     * @return ID del usuario insertado
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarUsuario(usuario: Usuario): Long
    

    @Update
    suspend fun actualizarUsuario(usuario: Usuario)
    

    @Delete
    suspend fun eliminarUsuario(usuario: Usuario)
}
