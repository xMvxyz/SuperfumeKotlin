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
import com.SuperfumeKotlin.data.remote.ApiService
import com.SuperfumeKotlin.data.model.dto.request.*
import com.SuperfumeKotlin.data.model.dto.response.*
import com.SuperfumeKotlin.util.TokenManager
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
    private val remoteDataSource: RemoteDataSource? = null,
    private val apiService: ApiService,
    private val tokenManager: TokenManager
) {
    
    // ========== OPERACIONES DE PERFUMES ==========
    
    /**
     * Obtiene todos los perfumes disponibles
     * Intenta cargar desde el backend, si falla usa datos locales de Room
     * @return Flow con la lista de perfumes disponibles
     */
    fun obtenerTodosLosPerfumesDisponibles(): Flow<List<Perfume>> = flow {
        // Primero emite datos locales (cache)
        emitAll(daoPerfume.obtenerTodosLosPerfumesDisponibles())
        
        // Intenta actualizar desde el backend
        try {
            val response = apiService.getPerfumes()
            if (response.isSuccessful) {
                response.body()?.let { perfumesDto ->
                    // Convertir DTOs a modelos locales y guardar en Room
                    val perfumesLocales = perfumesDto.map { dto ->
                        Perfume(
                            id = dto.id.toLong(),
                            name = dto.nombre,
                            brand = dto.marca,
                            price = dto.precio.toInt(),
                            description = dto.descripcion ?: "",
                            imageUri = dto.imagenUrl,
                            gender = dto.genero ?: "Unisex",
                            fragancia = dto.fragancia ?: "General",
                            notas = dto.notas ?: "",
                            perfil = dto.perfil ?: "",
                            stock = dto.stock,
                            isAvailable = dto.stock > 0
                        )
                    }
                    // Guardar en Room para uso offline
                    perfumesLocales.forEach { daoPerfume.insertarPerfume(it) }
                }
            }
        } catch (e: Exception) {
            // Si falla la conexión, los datos locales ya fueron emitidos
            e.printStackTrace()
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
    
    // ========== OPERACIONES DE BACKEND API ==========
    
    /**
     * Inicia sesión usando el backend
     * Guarda el usuario en Room para acceso offline
     */
    suspend fun login(email: String, password: String): LoginResponse? {
        return try {
            val request = LoginRequest(email, password)
            val response = apiService.login(request)
            if (response.isSuccessful) {
                val loginResponse = response.body()
                // Guardar usuario en Room para acceso offline
                loginResponse?.usuario?.let { userDto ->
                    val usuario = Usuario(
                        id = userDto.id.toLong(),
                        email = userDto.correo,
                        password = "", // No guardar contraseña
                        firstName = userDto.nombre.split(" ").firstOrNull() ?: userDto.nombre,
                        lastName = userDto.nombre.split(" ").drop(1).joinToString(" "),
                        phone = userDto.telefono,
                        address = userDto.direccion
                    )
                    daoUsuario.insertarUsuario(usuario)
                }
                loginResponse
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            // Intentar autenticación offline
            val usuario = daoUsuario.autenticarUsuario(email, password)
            if (usuario != null) {
                // Crear respuesta simulada desde datos locales
                LoginResponse(
                    success = true,
                    mensaje = "Sesión offline",
                    usuario = null,
                    token = null
                )
            } else {
                null
            }
        }
    }
    
    /**
     * Registra un nuevo usuario usando el backend
     * Guarda el usuario en Room para acceso offline
     */
    suspend fun register(
        name: String,
        email: String,
        password: String,
        rut: String?,
        phone: String?,
        address: String?
    ): LoginResponse? {
        return try {
            val request = RegisterRequest(name, email, password, rut, phone, address)
            val response = apiService.register(request)
            if (response.isSuccessful) {
                val loginResponse = response.body()
                // Guardar usuario en Room
                loginResponse?.usuario?.let { userDto ->
                    val usuario = Usuario(
                        id = userDto.id.toLong(),
                        email = userDto.correo,
                        password = password, // Guardar para autenticación offline
                        firstName = name.split(" ").firstOrNull() ?: name,
                        lastName = name.split(" ").drop(1).joinToString(" "),
                        phone = phone,
                        address = address
                    )
                    daoUsuario.insertarUsuario(usuario)
                }
                loginResponse
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    
    /**
     * Guarda el token JWT
     */
    fun saveToken(token: String) {
        tokenManager.saveToken(token)
    }
    
    /**
     * Guarda el token JWT y la información del usuario incluyendo rol
     */
    suspend fun saveToken(token: String, userId: Int, email: String, roleId: Int, roleName: String) {
        tokenManager.saveToken(token)
        tokenManager.saveUserInfo(userId, email, roleId, roleName)
    }
    
    /**
     * Obtiene el token guardado
     */
    fun getToken(): String? = tokenManager.getToken()
    
    /**
     * Limpia el token y la sesión
     */
    fun clearSession() {
        tokenManager.clearAll()
    }
}
