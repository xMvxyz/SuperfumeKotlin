package com.SuperfumeKotlin.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.SuperfumeKotlin.data.model.ElementoCarrito
import com.SuperfumeKotlin.data.model.Perfume
import com.SuperfumeKotlin.data.repository.RepositorioSuperfume
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Tests para ViewModelCarrito
 * Cubre la funcionalidad del carrito de compras
 */
@OptIn(ExperimentalCoroutinesApi::class)
class CartViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repositorio: RepositorioSuperfume
    private lateinit var viewModel: ViewModelCarrito

    private val idUsuario = 1L
    private val idPerfume1 = 1L
    private val idPerfume2 = 2L

    private val perfume1 = Perfume(
        id = idPerfume1,
        name = "Perfume 1",
        brand = "Marca 1",
        description = "Desc 1",
        price = 10000,
        stock = 10,
        category = "Floral",
        gender = "Mujer",
        size = "50ml",
        imageUri = "url1",
        isAvailable = true
    )

    private val perfume2 = Perfume(
        id = idPerfume2,
        name = "Perfume 2",
        brand = "Marca 2",
        description = "Desc 2",
        price = 15000,
        stock = 5,
        category = "Amaderado",
        gender = "Hombre",
        size = "100ml",
        imageUri = "url2",
        isAvailable = true
    )

    private val elementoCarrito1 = ElementoCarrito(
        id = 1,
        userId = idUsuario,
        perfumeId = idPerfume1,
        quantity = 2,
        addedAt = System.currentTimeMillis()
    )

    private val elementoCarrito2 = ElementoCarrito(
        id = 2,
        userId = idUsuario,
        perfumeId = idPerfume2,
        quantity = 1,
        addedAt = System.currentTimeMillis()
    )

    @Before
    fun configurar() {
        Dispatchers.setMain(testDispatcher)
        repositorio = mockk(relaxed = true)
        viewModel = ViewModelCarrito(repositorio)
    }

    @After
    fun limpiar() {
        Dispatchers.resetMain()
    }

    @Test
    fun `cargarElementosCarrito debe cargar elementos y calcular totales correctamente`() = runTest {
        // Given
        val elementos = listOf(elementoCarrito1, elementoCarrito2)
        coEvery { repositorio.obtenerElementosCarritoPorUsuario(idUsuario) } returns flowOf(elementos)
        coEvery { repositorio.obtenerPerfumePorId(idPerfume1) } returns perfume1
        coEvery { repositorio.obtenerPerfumePorId(idPerfume2) } returns perfume2

        // When
        viewModel.cargarElementosCarrito(idUsuario)
        advanceUntilIdle()

        // Then
        assertEquals(elementos, viewModel.elementosCarrito.value)

        val perfumes = viewModel.perfumesCarrito.value
        assertEquals(2, perfumes.size)
        assertTrue(perfumes.contains(perfume1))
        assertTrue(perfumes.contains(perfume2))

        assertEquals(3, viewModel.totalElementos.value) // 2 + 1
        assertEquals(35000, viewModel.precioTotal.value) // (10000 * 2) + (15000 * 1)
    }

    @Test
    fun `cargarElementosCarrito debe manejar errores correctamente`() = runTest {
        // Given
        coEvery { repositorio.obtenerElementosCarritoPorUsuario(idUsuario) } throws Exception("Error de BD")

        // When
        viewModel.cargarElementosCarrito(idUsuario)
        advanceUntilIdle()

        // Then
        val error = viewModel.mensajeError.value
        assertTrue(error?.contains("Error al cargar el carrito") == true)
    }

    @Test
    fun `agregarAlCarrito debe crear nuevo elemento si no existe`() = runTest {
        // Given
        coEvery { repositorio.obtenerElementoCarrito(idUsuario, idPerfume1) } returns null
        coEvery { repositorio.agregarAlCarrito(any()) } returns 1L
        coEvery { repositorio.obtenerElementosCarritoPorUsuario(idUsuario) } returns flowOf(emptyList())

        // When
        viewModel.agregarAlCarrito(idUsuario, idPerfume1, 2)
        advanceUntilIdle()

        // Then
        coVerify { repositorio.agregarAlCarrito(any()) }
        coVerify { repositorio.obtenerElementosCarritoPorUsuario(idUsuario) }
    }

    @Test
    fun `agregarAlCarrito debe manejar errores correctamente`() = runTest {
        // Given
        coEvery { repositorio.obtenerElementoCarrito(idUsuario, idPerfume1) } throws Exception("Error de BD")

        // When
        viewModel.agregarAlCarrito(idUsuario, idPerfume1, 1)
        advanceUntilIdle()

        // Then
        val error = viewModel.mensajeError.value
        assertTrue(error?.contains("Error al agregar al carrito") == true)
    }

    @Test
    fun `actualizarCantidadElemento debe actualizar la cantidad correctamente`() = runTest {
        // Given
        val nuevaCantidad = 5
        coEvery { repositorio.obtenerElementoCarrito(idUsuario, idPerfume1) } returns elementoCarrito1
        coEvery { repositorio.actualizarElementoCarrito(any()) } returns Unit
        coEvery { repositorio.obtenerElementosCarritoPorUsuario(idUsuario) } returns flowOf(emptyList())

        // When
        viewModel.actualizarCantidadElemento(idUsuario, idPerfume1, nuevaCantidad)
        advanceUntilIdle()

        // Then
        coVerify { 
            repositorio.actualizarElementoCarrito(match { 
                it.quantity == nuevaCantidad 
            }) 
        }
    }

    @Test
    fun `eliminarDelCarrito debe eliminar el elemento correctamente`() = runTest {
        // Given
        coEvery { repositorio.eliminarDelCarrito(idUsuario, idPerfume1) } returns Unit
        coEvery { repositorio.obtenerElementosCarritoPorUsuario(idUsuario) } returns flowOf(emptyList())

        // When
        viewModel.eliminarDelCarrito(idUsuario, idPerfume1)
        advanceUntilIdle()

        // Then
        coVerify { repositorio.eliminarDelCarrito(idUsuario, idPerfume1) }
    }

    @Test
    fun `cargarElementosCarrito con carrito vacio debe tener totales en cero`() = runTest {
        // Given
        coEvery { repositorio.obtenerElementosCarritoPorUsuario(idUsuario) } returns flowOf(emptyList())

        // When
        viewModel.cargarElementosCarrito(idUsuario)
        advanceUntilIdle()

        // Then
        assertEquals(0, viewModel.totalElementos.value)
        assertEquals(0, viewModel.precioTotal.value)
    }
}
