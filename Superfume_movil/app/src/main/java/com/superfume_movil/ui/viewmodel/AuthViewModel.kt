package com.superfume_movil.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superfume_movil.data.model.Usuario
import com.superfume_movil.data.repository.RepositorioSuperfume
import com.superfume_movil.util.RecursosTexto
import com.superfume_movil.util.ValidadorFormularios
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para la autenticación de usuarios
 * Maneja el login, registro y estado de autenticación
 */
@HiltViewModel
class ViewModelAutenticacion @Inject constructor(
    private val repositorio: RepositorioSuperfume
) : ViewModel() {
    
    private val _usuarioActual = MutableStateFlow<Usuario?>(null)
    val usuarioActual: StateFlow<Usuario?> = _usuarioActual.asStateFlow()
    
    private val _estaLogueado = MutableStateFlow(false)
    val estaLogueado: StateFlow<Boolean> = _estaLogueado.asStateFlow()
    
    private val _estaCargando = MutableStateFlow(false)
    val estaCargando: StateFlow<Boolean> = _estaCargando.asStateFlow()
    
    private val _mensajeError = MutableStateFlow<String?>(null)
    val mensajeError: StateFlow<String?> = _mensajeError.asStateFlow()
    
    private val _formularioLogin = MutableStateFlow(FormularioLogin())
    val formularioLogin: StateFlow<FormularioLogin> = _formularioLogin.asStateFlow()
    
    private val _formularioRegistro = MutableStateFlow(FormularioRegistro())
    val formularioRegistro: StateFlow<FormularioRegistro> = _formularioRegistro.asStateFlow()
    
    /**
     * Inicia sesión con email y contraseña
     * @param email Email del usuario
     * @param contraseña Contraseña del usuario
     */
    fun iniciarSesion(email: String, contraseña: String) {
        val validacionEmail = ValidadorFormularios.validarEmail(email)
        if (!validacionEmail.esValido) {
            _mensajeError.value = validacionEmail.mensajeError
            return
        }
        
        val validacionContraseña = ValidadorFormularios.validarContraseña(contraseña)
        if (!validacionContraseña.esValido) {
            _mensajeError.value = validacionContraseña.mensajeError
            return
        }
        
        viewModelScope.launch {
            _estaCargando.value = true
            try {
                val usuario = repositorio.autenticarUsuario(email, contraseña)
                if (usuario != null) {
                    _usuarioActual.value = usuario
                    _estaLogueado.value = true
                    _mensajeError.value = null
                } else {
                    _mensajeError.value = RecursosTexto.ERROR_CREDENCIALES_INVALIDAS
                }
            } catch (e: Exception) {
                _mensajeError.value = "Error al iniciar sesión: ${e.message}"
            } finally {
                _estaCargando.value = false
            }
        }
    }
    
    /**
     * Registra un nuevo usuario
     * @param usuario Usuario a registrar
     */
    fun registrarUsuario(usuario: Usuario) {
        val validacionEmail = ValidadorFormularios.validarEmail(usuario.email)
        if (!validacionEmail.esValido) {
            _mensajeError.value = validacionEmail.mensajeError
            return
        }
        
        val validacionContraseña = ValidadorFormularios.validarContraseña(usuario.password)
        if (!validacionContraseña.esValido) {
            _mensajeError.value = validacionContraseña.mensajeError
            return
        }
        
        val validacionNombre = ValidadorFormularios.validarNombre(usuario.firstName)
        if (!validacionNombre.esValido) {
            _mensajeError.value = validacionNombre.mensajeError
            return
        }
        
        val validacionApellido = ValidadorFormularios.validarNombre(usuario.lastName)
        if (!validacionApellido.esValido) {
            _mensajeError.value = validacionApellido.mensajeError
            return
        }
        
        viewModelScope.launch {
            _estaCargando.value = true
            try {
                val usuarioExistente = repositorio.obtenerUsuarioPorEmail(usuario.email)
                if (usuarioExistente != null) {
                    _mensajeError.value = RecursosTexto.ERROR_EMAIL_REGISTRADO
                } else {
                    val idUsuario = repositorio.insertarUsuario(usuario)
                    _usuarioActual.value = usuario.copy(id = idUsuario)
                    _estaLogueado.value = true
                    _mensajeError.value = null
                }
            } catch (e: Exception) {
                _mensajeError.value = "Error al registrar usuario: ${e.message}"
            } finally {
                _estaCargando.value = false
            }
        }
    }
    
    /**
     * Cierra la sesión del usuario actual
     */
    fun cerrarSesion() {
        _usuarioActual.value = null
        _estaLogueado.value = false
        _formularioLogin.value = FormularioLogin()
        _formularioRegistro.value = FormularioRegistro()
    }
    
    /**
     * Actualiza el formulario de login
     */
    fun actualizarFormularioLogin(email: String, contraseña: String) {
        _formularioLogin.value = FormularioLogin(email = email, contraseña = contraseña)
    }
    
    /**
     * Actualiza el formulario de registro
     */
    fun actualizarFormularioRegistro(
        email: String,
        contraseña: String,
        nombre: String,
        apellido: String,
        telefono: String = "",
        direccion: String = ""
    ) {
        _formularioRegistro.value = FormularioRegistro(
            email = email,
            contraseña = contraseña,
            nombre = nombre,
            apellido = apellido,
            telefono = telefono,
            direccion = direccion
        )
    }
    
    /**
     * Limpia el mensaje de error
     */
    fun limpiarError() {
        _mensajeError.value = null
    }
}

/**
 * Clase de datos para el formulario de login
 */
data class FormularioLogin(
    val email: String = "",
    val contraseña: String = ""
)

/**
 * Clase de datos para el formulario de registro
 */
data class FormularioRegistro(
    val email: String = "",
    val contraseña: String = "",
    val nombre: String = "",
    val apellido: String = "",
    val telefono: String = "",
    val direccion: String = ""
)
