package com.SuperfumeKotlin.data.repository

import com.SuperfumeKotlin.data.dao.DaoCarrito
import com.SuperfumeKotlin.data.dao.DaoPerfume
import com.SuperfumeKotlin.data.dao.DaoUsuario
import com.SuperfumeKotlin.data.model.ElementoCarrito
import com.SuperfumeKotlin.data.model.Perfume
import com.SuperfumeKotlin.data.model.Usuario
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.emitAll
import com.SuperfumeKotlin.data.remote.RemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repositorio principal de la aplicación Superfume
 * Centraliza todas las operaciones de datos y proporciona una interfaz única
 * para acceder a los datos de perfumes, usuarios y carrito
 */
@Singleton
class RepositorioSuperfume @Inject constructor(
    private val daoPerfume: DaoPerfume,
    private val daoUsuario: DaoUsuario,
    private val daoCarrito: DaoCarrito,
    private val remoteDataSource: RemoteDataSource? = null
) {
    
    // ========== OPERACIONES DE PERFUMES ==========
    
    /**
     * Obtiene todos los perfumes disponibles
     * @return Flow con la lista de perfumes disponibles
     */
    fun obtenerTodosLosPerfumesDisponibles(): Flow<List<Perfume>> = flow {
        // Emite primero los datos locales desde Room
        emitAll(daoPerfume.obtenerTodosLosPerfumesDisponibles())

        // Intenta refrescar desde el backend y guardar en la BD local
        try {
            val remotos = remoteDataSource?.obtenerPerfumes() ?: emptyList()
            if (remotos.isNotEmpty()) {
                remotos.forEach { daoPerfume.insertarPerfume(it) }
            }
        } catch (_: Exception) {
            // Ignorar errores de red; mantenemos datos locales
        }
    }
    
    /**
     * Obtiene un perfume por su ID
     * @param id ID del perfume
     * @return Perfume encontrado o null
     */
    suspend fun obtenerPerfumePorId(id: Long): Perfume? = 
        daoPerfume.obtenerPerfumePorId(id)
    
    /**
     * Busca perfumes por nombre o marca
     * @param consulta Texto de búsqueda
     * @return Flow con la lista de perfumes encontrados
     */
    fun buscarPerfumes(consulta: String): Flow<List<Perfume>> = 
        daoPerfume.buscarPerfumes(consulta)
    
    /**
     * Obtiene perfumes por categoría
     * @param categoria Categoría a filtrar
     * @return Flow con la lista de perfumes de la categoría
     */
    fun obtenerPerfumesPorCategoria(categoria: String): Flow<List<Perfume>> = 
        daoPerfume.obtenerPerfumesPorCategoria(categoria)
    
    /**
     * Obtiene perfumes por género
     * @param genero Género a filtrar
     * @return Flow con la lista de perfumes del género
     */
    fun obtenerPerfumesPorGenero(genero: String): Flow<List<Perfume>> = 
        daoPerfume.obtenerPerfumesPorGenero(genero)
    
    /**
     * Inserta un nuevo perfume
     * @param perfume Perfume a insertar
     * @return ID del perfume insertado
     */
    suspend fun insertarPerfume(perfume: Perfume): Long = 
        daoPerfume.insertarPerfume(perfume)
    
    /**
     * Actualiza un perfume existente
     * @param perfume Perfume a actualizar
     */
    suspend fun actualizarPerfume(perfume: Perfume) = 
        daoPerfume.actualizarPerfume(perfume)
    
    /**
     * Elimina un perfume
     * @param perfume Perfume a eliminar
     */
    suspend fun eliminarPerfume(perfume: Perfume) = 
        daoPerfume.eliminarPerfume(perfume)
    
    /**
     * Actualiza el stock de un perfume
     * @param idPerfume ID del perfume
     * @param nuevoStock Nuevo valor de stock
     */
    suspend fun actualizarStock(idPerfume: Long, nuevoStock: Int) = 
        daoPerfume.actualizarStock(idPerfume, nuevoStock)
    
    // ========== OPERACIONES DE USUARIOS ==========
    
    /**
     * Autentica un usuario
     * @param email Email del usuario
     * @param contraseña Contraseña del usuario
     * @return Usuario autenticado o null si las credenciales son inválidas
     */
    suspend fun autenticarUsuario(email: String, contraseña: String): Usuario? = 
        daoUsuario.autenticarUsuario(email, contraseña)
    
    /**
     * Obtiene un usuario por su email
     * @param email Email del usuario
     * @return Usuario encontrado o null
     */
    suspend fun obtenerUsuarioPorEmail(email: String): Usuario? = 
        daoUsuario.obtenerUsuarioPorEmail(email)
    
    /**
     * Obtiene un usuario por su ID
     * @param id ID del usuario
     * @return Usuario encontrado o null
     */
    suspend fun obtenerUsuarioPorId(id: Long): Usuario? = 
        daoUsuario.obtenerUsuarioPorId(id)
    
    /**
     * Inserta un nuevo usuario
     * @param usuario Usuario a insertar
     * @return ID del usuario insertado
     */
    suspend fun insertarUsuario(usuario: Usuario): Long = 
        daoUsuario.insertarUsuario(usuario)
    
    /**
     * Actualiza un usuario existente
     * @param usuario Usuario a actualizar
     */
    suspend fun actualizarUsuario(usuario: Usuario) = 
        daoUsuario.actualizarUsuario(usuario)
    
    /**
     * Elimina un usuario
     * @param usuario Usuario a eliminar
     */
    suspend fun eliminarUsuario(usuario: Usuario) = 
        daoUsuario.eliminarUsuario(usuario)
    
    // ========== OPERACIONES DEL CARRITO ==========
    
    /**
     * Obtiene todos los elementos del carrito de un usuario
     * @param idUsuario ID del usuario
     * @return Flow con la lista de elementos del carrito
     */
    fun obtenerElementosCarritoPorUsuario(idUsuario: Long): Flow<List<ElementoCarrito>> = 
        daoCarrito.obtenerElementosCarritoPorUsuario(idUsuario)
    
    /**
     * Obtiene un elemento específico del carrito
     * @param idUsuario ID del usuario
     * @param idPerfume ID del perfume
     * @return Elemento del carrito o null si no existe
     */
    suspend fun obtenerElementoCarrito(idUsuario: Long, idPerfume: Long): ElementoCarrito? = 
        daoCarrito.obtenerElementoCarrito(idUsuario, idPerfume)
    
    /**
     * Agrega un elemento al carrito
     * @param elementoCarrito Elemento a agregar
     * @return ID del elemento agregado
     */
    suspend fun agregarAlCarrito(elementoCarrito: ElementoCarrito): Long = 
        daoCarrito.insertarElementoCarrito(elementoCarrito)
    
    /**
     * Actualiza un elemento del carrito
     * @param elementoCarrito Elemento a actualizar
     */
    suspend fun actualizarElementoCarrito(elementoCarrito: ElementoCarrito) = 
        daoCarrito.actualizarElementoCarrito(elementoCarrito)
    
    /**
     * Elimina un elemento del carrito
     * @param idUsuario ID del usuario
     * @param idPerfume ID del perfume a eliminar
     */
    suspend fun eliminarDelCarrito(idUsuario: Long, idPerfume: Long) = 
        daoCarrito.eliminarDelCarrito(idUsuario, idPerfume)
    
    /**
     * Vacía completamente el carrito de un usuario
     * @param idUsuario ID del usuario
     */
    suspend fun vaciarCarritoPorUsuario(idUsuario: Long) = 
        daoCarrito.vaciarCarritoPorUsuario(idUsuario)
}
