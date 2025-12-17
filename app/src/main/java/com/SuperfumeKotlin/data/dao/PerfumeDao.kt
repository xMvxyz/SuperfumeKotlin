package com.SuperfumeKotlin.data.dao

import androidx.room.*
import com.SuperfumeKotlin.data.model.Perfume
import com.SuperfumeKotlin.util.Constants
import kotlinx.coroutines.flow.Flow


@Dao
interface DaoPerfume {
    

    @Query("SELECT * FROM ${Constants.TABLE_PERFUMES} WHERE isAvailable = 1")
    fun obtenerTodosLosPerfumesDisponibles(): Flow<List<Perfume>>
    

    @Query("SELECT * FROM ${Constants.TABLE_PERFUMES} WHERE id = :id")
    suspend fun obtenerPerfumePorId(id: Long): Perfume?
    

    @Query("SELECT * FROM ${Constants.TABLE_PERFUMES} WHERE name LIKE '%' || :consultaBusqueda || '%' OR brand LIKE '%' || :consultaBusqueda || '%'")
    fun buscarPerfumes(consultaBusqueda: String): Flow<List<Perfume>>
    
 
    @Query("SELECT * FROM ${Constants.TABLE_PERFUMES} WHERE category = :categoria")
    fun obtenerPerfumesPorCategoria(categoria: String): Flow<List<Perfume>>
    

    @Query("SELECT * FROM ${Constants.TABLE_PERFUMES} WHERE gender = :genero")
    fun obtenerPerfumesPorGenero(genero: String): Flow<List<Perfume>>
    

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarPerfume(perfume: Perfume): Long
    

    @Update
    suspend fun actualizarPerfume(perfume: Perfume)
    

    @Delete
    suspend fun eliminarPerfume(perfume: Perfume)
    
 
    @Query("UPDATE ${Constants.TABLE_PERFUMES} SET stock = :nuevoStock WHERE id = :idPerfume")
    suspend fun actualizarStock(idPerfume: Long, nuevoStock: Int)
}
