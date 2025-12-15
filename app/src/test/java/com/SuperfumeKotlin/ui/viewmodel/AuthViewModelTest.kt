package com.SuperfumeKotlin.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.SuperfumeKotlin.data.model.Usuario
import com.SuperfumeKotlin.data.repository.RepositorioSuperfume
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull

/**
 * Tests para ViewModelAutenticacion
 * 
 * NOTA: Los tests de login y registro que requieren validación de email están omitidos
 * debido a dependencias de Android (Patterns.EMAIL_ADDRESS en FormValidators).
 * 
 * Estos tests cubren funcionalidades que no dependen de validaciones de Android.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class AuthViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repositorio: RepositorioSuperfume
    private lateinit var viewModel: ViewModelAutenticacion

    @Before
    fun configurar() {
        Dispatchers.setMain(testDispatcher)
        repositorio = mockk(relaxed = true)
        viewModel = ViewModelAutenticacion(repositorio)
    }

    @After
    fun limpiar() {
        Dispatchers.resetMain()
    }

    @Test
    fun `cerrarSesion debe limpiar usuario actual y establecer estaLogueado en false`() {
        // Given - establecer un usuario en el viewModel
        val usuario = Usuario(
            id = 1,
            firstName = "Juan",
            lastName = "Pérez",
            email = "juan@example.com",
            password = "password",
            phone = "123456789",
            address = "Calle 123",
            profileImageUri = null
        )
        
        // When - cerrar sesión
        viewModel.cerrarSesion()

        // Then - verificar que el estado se limpió
        assertNull(viewModel.usuarioActual.value)
        assertFalse(viewModel.estaLogueado.value)
    }

    @Test
    fun `limpiarError debe establecer mensajeError en null`() {
        // When - limpiar error
        viewModel.limpiarError()

        // Then - verificar que no hay mensaje de error
        assertNull(viewModel.mensajeError.value)
    }

    @Test
    fun `estado inicial debe tener valores por defecto`() {
        // Then - verificar estado inicial
        assertNull(viewModel.usuarioActual.value)
        assertFalse(viewModel.estaLogueado.value)
        assertFalse(viewModel.estaCargando.value)
        assertNull(viewModel.mensajeError.value)
        assertEquals("", viewModel.formularioLogin.value.email)
        assertEquals("", viewModel.formularioLogin.value.contraseña)
    }

    @Test
    fun `actualizarFormularioLogin debe actualizar el formulario correctamente`() {
        // Given
        val email = "test@example.com"
        val contraseña = "password123"

        // When
        viewModel.actualizarFormularioLogin(email, contraseña)

        // Then
        assertEquals(email, viewModel.formularioLogin.value.email)
        assertEquals(contraseña, viewModel.formularioLogin.value.contraseña)
    }

    @Test
    fun `actualizarFormularioRegistro debe actualizar el formulario correctamente`() {
        // Given
        val email = "nuevo@example.com"
        val contraseña = "password456"
        val nombre = "Maria"
        val apellido = "García"
        val telefono = "987654321"
        val direccion = "Avenida 456"

        // When
        viewModel.actualizarFormularioRegistro(
            email = email,
            contraseña = contraseña,
            nombre = nombre,
            apellido = apellido,
            telefono = telefono,
            direccion = direccion
        )

        // Then
        assertEquals(email, viewModel.formularioRegistro.value.email)
        assertEquals(contraseña, viewModel.formularioRegistro.value.contraseña)
        assertEquals(nombre, viewModel.formularioRegistro.value.nombre)
        assertEquals(apellido, viewModel.formularioRegistro.value.apellido)
        assertEquals(telefono, viewModel.formularioRegistro.value.telefono)
        assertEquals(direccion, viewModel.formularioRegistro.value.direccion)
    }
}
