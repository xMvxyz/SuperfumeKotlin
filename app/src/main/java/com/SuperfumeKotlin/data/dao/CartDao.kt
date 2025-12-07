package com.SuperfumeKotlin.data.dao

import androidx.room.*
import com.SuperfumeKotlin.data.model.ElementoCarrito
import com.SuperfumeKotlin.util.Constantes
import kotlinx.coroutines.flow.Flow

/**
 * DAO para operaciones del carrito de compras
 * Maneja todas las operaciones relacionadas con elementos del carrito
 */
@Dao
interface DaoCarrito {
    
    /**
     * Obtiene todos los elementos del carrito de un usuario
     * @param idUsuario ID del usuario
     * @return Flow con la lista de elementos del carrito
     */
    @Query("SELECT * FROM ${Constantes.TABLA_CARRITO} WHERE userId = :idUsuario")
    fun obtenerElementosCarritoPorUsuario(idUsuario: Long): Flow<List<ElementoCarrito>>
    
    /**
     * Obtiene un elemento específico del carrito
     * @param idUsuario ID del usuario
     * @param idPerfume ID del perfume
     * @return Elemento del carrito o null si no existe
     */
    @Query("SELECT * FROM ${Constantes.TABLA_CARRITO} WHERE userId = :idUsuario AND perfumeId = :idPerfume")
    suspend fun obtenerElementoCarrito(idUsuario: Long, idPerfume: Long): ElementoCarrito?
    
    /**
     * Inserta un nuevo elemento al carrito
     * @param elementoCarrito Elemento a insertar
     * @return ID del elemento insertado
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarElementoCarrito(elementoCarrito: ElementoCarrito): Long
    
    /**
     * Actualiza un elemento del carrito
     * @param elementoCarrito Elemento a actualizar
     */
    @Update
    suspend fun actualizarElementoCarrito(elementoCarrito: ElementoCarrito)
    
    /**
     * Elimina un elemento del carrito
     * @param elementoCarrito Elemento a eliminar
     */
    @Delete
    suspend fun eliminarElementoCarrito(elementoCarrito: ElementoCarrito)
    
    /**
     * Vacía completamente el carrito de un usuario
     * @param idUsuario ID del usuario
     */
    @Query("DELETE FROM ${Constantes.TABLA_CARRITO} WHERE userId = :idUsuario")
    suspend fun vaciarCarrito(idUsuario: Long)
    
    @Query("DELETE FROM ${Constantes.TABLA_CARRITO} WHERE userId = :idUsuario AND perfumeId = :idPerfume")
    suspend fun eliminarDelCarrito(idUsuario: Long, idPerfume: Long)
    
    /**
     * Vacía completamente el carrito de un usuario (alias para vaciarCarrito)
     * @param idUsuario ID del usuario
     */
    suspend fun vaciarCarritoPorUsuario(idUsuario: Long) = vaciarCarrito(idUsuario)
}
