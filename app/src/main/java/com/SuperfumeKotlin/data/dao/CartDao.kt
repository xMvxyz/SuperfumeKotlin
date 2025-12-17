package com.SuperfumeKotlin.data.dao

import androidx.room.*
import com.SuperfumeKotlin.data.model.ElementoCarrito
import com.SuperfumeKotlin.util.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoCarrito {
    

    @Query("SELECT * FROM ${Constants.TABLE_CART} WHERE userId = :idUsuario")
    fun obtenerElementosCarritoPorUsuario(idUsuario: Long): Flow<List<ElementoCarrito>>
    
    @Query("SELECT * FROM ${Constants.TABLE_CART} WHERE userId = :idUsuario AND perfumeId = :idPerfume")
    suspend fun obtenerElementoCarrito(idUsuario: Long, idPerfume: Long): ElementoCarrito?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarElementoCarrito(elementoCarrito: ElementoCarrito): Long
    
    @Update
    suspend fun actualizarElementoCarrito(elementoCarrito: ElementoCarrito)
    
    @Delete
    suspend fun eliminarElementoCarrito(elementoCarrito: ElementoCarrito)
    

    @Query("DELETE FROM ${Constants.TABLE_CART} WHERE userId = :idUsuario")
    suspend fun vaciarCarrito(idUsuario: Long)
    
    @Query("DELETE FROM ${Constants.TABLE_CART} WHERE userId = :idUsuario AND perfumeId = :idPerfume")
    suspend fun eliminarDelCarrito(idUsuario: Long, idPerfume: Long)
    

    suspend fun vaciarCarritoPorUsuario(idUsuario: Long) = vaciarCarrito(idUsuario)
}
