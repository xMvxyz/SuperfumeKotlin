package com.SuperfumeKotlin.data.repository

import app.cash.turbine.test
import com.SuperfumeKotlin.data.dao.DaoCarrito
import com.SuperfumeKotlin.data.dao.DaoPerfume
import com.SuperfumeKotlin.data.dao.DaoUsuario
import com.SuperfumeKotlin.data.model.ElementoCarrito
import com.SuperfumeKotlin.data.model.Perfume
import com.SuperfumeKotlin.data.model.Usuario
import com.SuperfumeKotlin.data.remote.RemoteDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

/**
 * Tests para RepositorioSuperfume
 * Cubre las operaciones de acceso a datos para perfumes, usuarios y carrito
 */
@OptIn(ExperimentalCoroutinesApi::class)
class SuperfumeRepositoryTest {

    private lateinit var daoPerfume: DaoPerfume
    private lateinit var daoUsuario: DaoUsuario
    private lateinit var daoCarrito: DaoCarrito
    private lateinit var remoteDataSource: RemoteDataSource
    private lateinit var repositorio: RepositorioSuperfume

    private val perfumeEjemplo = Perfume(
        id = 1,
        name = "Perfume Test",
        brand = "Marca Test",
        description = "Descripción test",
        price = 10000,
        stock = 10,
        category = "Floral",
        gender = "Unisex",
        size = "50ml",
        imageUri = "url",
        isAvailable = true
    )

    private val usuarioEjemplo = Usuario(
        id = 1,
        firstName = "Test",
        lastName = "User",
        email = "test@example.com",
        password = "password123",
        phone = "123456789",
        address = "Dirección test",
        profileImageUri = null
    )

    private val elementoCarritoEjemplo = ElementoCarrito(
        id = 1,
        userId = 1,
        perfumeId = 1,
        quantity = 2,
        addedAt = System.currentTimeMillis()
    )

    @Before
    fun configurar() {
        daoPerfume = mockk(relaxed = true)
        daoUsuario = mockk(relaxed = true)
        daoCarrito = mockk(relaxed = true)
        remoteDataSource = mockk(relaxed = true)
        repositorio = RepositorioSuperfume(daoPerfume, daoUsuario, daoCarrito, remoteDataSource)
    }

    // ========== TESTS DE PERFUMES ==========

    @Test
    fun `obtenerTodosLosPerfumesDisponibles debe retornar perfumes de Room`() = runTest {
        // Given
        val perfumes = listOf(perfumeEjemplo)
        coEvery { daoPerfume.obtenerTodosLosPerfumesDisponibles() } returns flowOf(perfumes)
        coEvery { remoteDataSource.obtenerPerfumes() } returns emptyList()

        // When
        repositorio.obtenerTodosLosPerfumesDisponibles().test {
            // Then
            assertEquals(perfumes, awaitItem())
            awaitComplete()
        }

        coVerify { daoPerfume.obtenerTodosLosPerfumesDisponibles() }
    }

    @Test
    fun `obtenerPerfumePorId debe retornar perfume correcto`() = runTest {
        // Given
        val id = 1L
        coEvery { daoPerfume.obtenerPerfumePorId(id) } returns perfumeEjemplo

        // When
        val resultado = repositorio.obtenerPerfumePorId(id)

        // Then
        assertEquals(perfumeEjemplo, resultado)
        coVerify { daoPerfume.obtenerPerfumePorId(id) }
    }

    @Test
    fun `insertarPerfume debe llamar al dao y retornar id`() = runTest {
        // Given
        val idEsperado = 5L
        coEvery { daoPerfume.insertarPerfume(perfumeEjemplo) } returns idEsperado

        // When
        val resultado = repositorio.insertarPerfume(perfumeEjemplo)

        // Then
        assertEquals(idEsperado, resultado)
        coVerify { daoPerfume.insertarPerfume(perfumeEjemplo) }
    }

    // ========== TESTS DE USUARIOS ==========

    @Test
    fun `autenticarUsuario con credenciales validas debe retornar usuario`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "password123"
        coEvery { daoUsuario.autenticarUsuario(email, password) } returns usuarioEjemplo

        // When
        val resultado = repositorio.autenticarUsuario(email, password)

        // Then
        assertNotNull(resultado)
        assertEquals(usuarioEjemplo, resultado)
        coVerify { daoUsuario.autenticarUsuario(email, password) }
    }

    // ========== TESTS DE CARRITO ==========

    @Test
    fun `obtenerElementosCarritoPorUsuario debe retornar elementos del usuario`() = runTest {
        // Given
        val idUsuario = 1L
        val elementos = listOf(elementoCarritoEjemplo)
        coEvery { daoCarrito.obtenerElementosCarritoPorUsuario(idUsuario) } returns flowOf(elementos)

        // When
        repositorio.obtenerElementosCarritoPorUsuario(idUsuario).test {
            // Then
            assertEquals(elementos, awaitItem())
            awaitComplete()
        }

        coVerify { daoCarrito.obtenerElementosCarritoPorUsuario(idUsuario) }
    }

    @Test
    fun `agregarAlCarrito debe llamar al dao`() = runTest {
        // Given
        coEvery { daoCarrito.insertarElementoCarrito(elementoCarritoEjemplo) } returns 1L

        // When
        repositorio.agregarAlCarrito(elementoCarritoEjemplo)

        // Then
        coVerify { daoCarrito.insertarElementoCarrito(elementoCarritoEjemplo) }
    }

    @Test
    fun `eliminarDelCarrito debe llamar al dao`() = runTest {
        // Given
        val idUsuario = 1L
        val idPerfume = 1L
        coEvery { daoCarrito.eliminarDelCarrito(idUsuario, idPerfume) } returns Unit

        // When
        repositorio.eliminarDelCarrito(idUsuario, idPerfume)

        // Then
        coVerify { daoCarrito.eliminarDelCarrito(idUsuario, idPerfume) }
    }

}
