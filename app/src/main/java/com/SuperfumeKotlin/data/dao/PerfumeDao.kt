package com.SuperfumeKotlin.data.dao

import androidx.room.*
import com.SuperfumeKotlin.data.model.Perfume
import com.SuperfumeKotlin.util.Constants
import kotlinx.coroutines.flow.Flow

/**
 * DAO para operaciones de perfumes en la base de datos
 * Maneja todas las operaciones CRUD relacionadas con perfumes
 */
@Dao
interface DaoPerfume {
    
    /**
     * Obtiene todos los perfumes disponibles
     * @return Flow con la lista de perfumes disponibles
     */
    @Query("SELECT * FROM ${Constants.TABLE_PERFUMES} WHERE isAvailable = 1")
    fun obtenerTodosLosPerfumesDisponibles(): Flow<List<Perfume>>
    
    /**
     * Obtiene un perfume por su ID
     * @param id ID del perfume
     * @return Perfume encontrado o null
     */
    @Query("SELECT * FROM ${Constants.TABLE_PERFUMES} WHERE id = :id")
    suspend fun obtenerPerfumePorId(id: Long): Perfume?
    
    /**
     * Busca perfumes por nombre o marca
     * @param consultaBusqueda Texto de búsqueda
     * @return Flow con la lista de perfumes encontrados
     */
    @Query("SELECT * FROM ${Constants.TABLE_PERFUMES} WHERE name LIKE '%' || :consultaBusqueda || '%' OR brand LIKE '%' || :consultaBusqueda || '%'")
    fun buscarPerfumes(consultaBusqueda: String): Flow<List<Perfume>>
    
    /**
     * Obtiene perfumes por categoría
     * @param categoria Categoría a filtrar
     * @return Flow con la lista de perfumes de la categoría
     */
    @Query("SELECT * FROM ${Constants.TABLE_PERFUMES} WHERE category = :categoria")
    fun obtenerPerfumesPorCategoria(categoria: String): Flow<List<Perfume>>
    
    /**
     * Obtiene perfumes por género
     * @param genero Género a filtrar
     * @return Flow con la lista de perfumes del género
     */
    @Query("SELECT * FROM ${Constants.TABLE_PERFUMES} WHERE gender = :genero")
    fun obtenerPerfumesPorGenero(genero: String): Flow<List<Perfume>>
    
    /**
     * Inserta un nuevo perfume
     * @param perfume Perfume a insertar
     * @return ID del perfume insertado
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarPerfume(perfume: Perfume): Long
    
    /**
     * Actualiza un perfume existente
     * @param perfume Perfume a actualizar
     */
    @Update
    suspend fun actualizarPerfume(perfume: Perfume)
    
    /**
     * Elimina un perfume
     * @param perfume Perfume a eliminar
     */
    @Delete
    suspend fun eliminarPerfume(perfume: Perfume)
    
    /**
     * Actualiza el stock de un perfume
     * @param idPerfume ID del perfume
     * @param nuevoStock Nuevo valor de stock
     */
    @Query("UPDATE ${Constants.TABLE_PERFUMES} SET stock = :nuevoStock WHERE id = :idPerfume")
    suspend fun actualizarStock(idPerfume: Long, nuevoStock: Int)
}
