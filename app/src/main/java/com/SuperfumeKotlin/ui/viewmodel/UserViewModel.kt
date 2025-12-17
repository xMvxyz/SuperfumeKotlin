package com.SuperfumeKotlin.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.SuperfumeKotlin.data.model.dto.request.ChangeRoleRequest
import com.SuperfumeKotlin.data.model.dto.request.UpdateUserRequest
import com.SuperfumeKotlin.data.model.dto.response.UsuarioResponse
import com.SuperfumeKotlin.data.remote.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para gestión de usuarios (Admin)
 */
@HiltViewModel
class UserViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {
    
    private val _usuarios = MutableStateFlow<List<UsuarioResponse>>(emptyList())
    val usuarios: StateFlow<List<UsuarioResponse>> = _usuarios.asStateFlow()
    
    private val _usuarioSeleccionado = MutableStateFlow<UsuarioResponse?>(null)
    val usuarioSeleccionado: StateFlow<UsuarioResponse?> = _usuarioSeleccionado.asStateFlow()
    
    private val _estaCargando = MutableStateFlow(false)
    val estaCargando: StateFlow<Boolean> = _estaCargando.asStateFlow()
    
    private val _mensajeError = MutableStateFlow<String?>(null)
    val mensajeError: StateFlow<String?> = _mensajeError.asStateFlow()
    
    private val _mensajeExito = MutableStateFlow<String?>(null)
    val mensajeExito: StateFlow<String?> = _mensajeExito.asStateFlow()
    
    private val _consultaBusqueda = MutableStateFlow("")
    val consultaBusqueda: StateFlow<String> = _consultaBusqueda.asStateFlow()
    
    /**
     * Carga todos los usuarios
     */
    fun cargarUsuarios() {
        viewModelScope.launch {
            _estaCargando.value = true
            _mensajeError.value = null
            try {
                val response = apiService.getUsuarios()
                if (response.isSuccessful) {
                    _usuarios.value = response.body() ?: emptyList()
                } else {
                    _mensajeError.value = "Error al cargar usuarios: ${response.message()}"
                }
            } catch (e: Exception) {
                _mensajeError.value = "Error de conexión: ${e.message}"
            } finally {
                _estaCargando.value = false
            }
        }
    }
    
    /**
     * Busca usuarios por nombre o correo
     */
    fun buscarUsuarios(query: String) {
        _consultaBusqueda.value = query
    }
    
    /**
     * Obtiene usuarios filtrados por búsqueda
     */
    fun getUsuariosFiltrados(): List<UsuarioResponse> {
        val query = _consultaBusqueda.value
        if (query.isBlank()) return _usuarios.value
        
        return _usuarios.value.filter {
            it.nombre.contains(query, ignoreCase = true) ||
            it.correo.contains(query, ignoreCase = true)
        }
    }
    
    /**
     * Selecciona un usuario para editar
     */
    fun seleccionarUsuario(usuario: UsuarioResponse?) {
        _usuarioSeleccionado.value = usuario
    }
    
    /**
     * Actualiza un usuario
     */
    fun actualizarUsuario(id: Int, request: UpdateUserRequest) {
        viewModelScope.launch {
            _estaCargando.value = true
            _mensajeError.value = null
            try {
                val response = apiService.updateUsuario(id, request)
                if (response.isSuccessful) {
                    _mensajeExito.value = "Usuario actualizado exitosamente"
                    cargarUsuarios()
                    _usuarioSeleccionado.value = null
                } else {
                    _mensajeError.value = "Error al actualizar usuario: ${response.message()}"
                }
            } catch (e: Exception) {
                _mensajeError.value = "Error de conexión: ${e.message}"
            } finally {
                _estaCargando.value = false
            }
        }
    }
    
    /**
     * Cambia el rol de un usuario
     */
    fun cambiarRol(id: Int, nuevoRolId: Int) {
        viewModelScope.launch {
            _estaCargando.value = true
            _mensajeError.value = null
            try {
                val response = apiService.changeUserRole(id, ChangeRoleRequest(nuevoRolId))
                if (response.isSuccessful) {
                    _mensajeExito.value = "Rol actualizado exitosamente"
                    cargarUsuarios()
                } else {
                    _mensajeError.value = "Error al cambiar rol: ${response.message()}"
                }
            } catch (e: Exception) {
                _mensajeError.value = "Error de conexión: ${e.message}"
            } finally {
                _estaCargando.value = false
            }
        }
    }
    
    /**
     * Elimina un usuario
     */
    fun eliminarUsuario(id: Int) {
        viewModelScope.launch {
            _estaCargando.value = true
            _mensajeError.value = null
            try {
                val response = apiService.deleteUsuario(id)
                if (response.isSuccessful) {
                    _mensajeExito.value = "Usuario eliminado exitosamente"
                    cargarUsuarios()
                } else {
                    _mensajeError.value = "Error al eliminar usuario: ${response.message()}"
                }
            } catch (e: Exception) {
                _mensajeError.value = "Error de conexión: ${e.message}"
            } finally {
                _estaCargando.value = false
            }
        }
    }
    
    /**
     * Limpia mensajes
     */
    fun limpiarMensajes() {
        _mensajeError.value = null
        _mensajeExito.value = null
    }
}
