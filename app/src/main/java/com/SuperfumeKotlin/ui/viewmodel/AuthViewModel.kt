package com.SuperfumeKotlin.ui.viewmodel

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.SuperfumeKotlin.data.model.Usuario
import com.SuperfumeKotlin.data.repository.RepositorioSuperfume
import com.SuperfumeKotlin.util.TextResources
import com.SuperfumeKotlin.util.FormValidators
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
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
    
    // Evento de navegación de un solo uso
    private val _eventoNavegacion = MutableSharedFlow<NavegacionEvento>()
    val eventoNavegacion: SharedFlow<NavegacionEvento> = _eventoNavegacion.asSharedFlow()

    /**
     * Crea un URI para una nueva imagen a ser capturada por la cámara.
     */
    fun createImageUri(context: Context): Uri {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "JPEG_${timeStamp}_"
        val storageDir = context.getExternalFilesDir("images")
        val imageFile = File.createTempFile(imageFileName, ".jpg", storageDir)
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            imageFile
        )
    }

    /**
     * Actualiza la imagen de perfil del usuario.
     */
    fun updateProfileImage(uri: Uri) {
        viewModelScope.launch {
            val currentUser = _usuarioActual.value
            if (currentUser != null) {
                val updatedUser = currentUser.copy(profileImageUri = uri.toString())
                repositorio.actualizarUsuario(updatedUser) // Suponiendo que tienes un método para actualizar
                _usuarioActual.value = updatedUser
            }
        }
    }
    
    /**
     * Inicia sesión con email y contraseña
     * @param email Email del usuario
     * @param contraseña Contraseña del usuario
     */
    fun iniciarSesion(email: String, contraseña: String) {
        // Validaciones básicas de campos vacíos
        if (email.isBlank()) {
            _mensajeError.value = "El email no puede estar vacío"
            return
        }
        
        if (contraseña.isBlank()) {
            _mensajeError.value = "La contraseña no puede estar vacía"
            return
        }
        
        viewModelScope.launch {
            _estaCargando.value = true
            _mensajeError.value = null
            try {
                val usuario = repositorio.autenticarUsuario(email.trim(), contraseña)
                if (usuario != null) {
                    _usuarioActual.value = usuario
                    _estaLogueado.value = true
                    _mensajeError.value = null
                    _eventoNavegacion.emit(NavegacionEvento.NavegarAHome)
                } else {
                    _mensajeError.value = "Email o contraseña incorrectos"
                    _estaLogueado.value = false
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _mensajeError.value = "Error al iniciar sesión: ${e.message ?: "Error desconocido"}"
                _estaLogueado.value = false
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
        // Limpiar mensaje de error anterior
        _mensajeError.value = null
        
        val validacionEmail = FormValidators.validateEmail(usuario.email)
        if (!validacionEmail.isValid) {
            _mensajeError.value = validacionEmail.errorMessage
            return
        }
        
        val validacionContraseña = FormValidators.validatePassword(usuario.password)
        if (!validacionContraseña.isValid) {
            _mensajeError.value = validacionContraseña.errorMessage
            return
        }
        
        val validacionNombre = FormValidators.validateName(usuario.firstName)
        if (!validacionNombre.isValid) {
            _mensajeError.value = validacionNombre.errorMessage
            return
        }
        
        val validacionApellido = FormValidators.validateName(usuario.lastName)
        if (!validacionApellido.isValid) {
            _mensajeError.value = validacionApellido.errorMessage
            return
        }
        
        viewModelScope.launch {
            _estaCargando.value = true
            _mensajeError.value = null
            try {
                val usuarioExistente = repositorio.obtenerUsuarioPorEmail(usuario.email.trim())
                if (usuarioExistente != null) {
                    _mensajeError.value = TextResources.ERROR_EMAIL_ALREADY_REGISTERED
                } else {
                    val idUsuario = repositorio.insertarUsuario(usuario.copy(email = usuario.email.trim()))
                    _usuarioActual.value = usuario.copy(id = idUsuario, email = usuario.email.trim())
                    _estaLogueado.value = true
                    _mensajeError.value = null
                    _eventoNavegacion.emit(NavegacionEvento.NavegarAHome)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _mensajeError.value = "Error al registrar usuario: ${e.message ?: "Error desconocido"}"
            } finally {
                _estaCargando.value = false
            }
        }
    }

    fun updateUser(firstName: String, lastName: String, phone: String, address: String) {
        viewModelScope.launch {
            val currentUser = _usuarioActual.value
            if (currentUser != null) {
                val updatedUser = currentUser.copy(
                    firstName = firstName,
                    lastName = lastName,
                    phone = phone,
                    address = address
                )
                repositorio.actualizarUsuario(updatedUser)
                _usuarioActual.value = updatedUser
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

/**
 * Eventos de navegación de un solo uso
 */
sealed class NavegacionEvento {
    object NavegarAHome : NavegacionEvento()
}
