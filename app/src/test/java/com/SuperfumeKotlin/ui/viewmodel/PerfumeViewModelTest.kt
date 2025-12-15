package com.SuperfumeKotlin.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.SuperfumeKotlin.data.model.Perfume
import com.SuperfumeKotlin.data.repository.RepositorioSuperfume
import com.SuperfumeKotlin.util.Constants
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
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
 * Tests para ViewModelPerfumes
 * Cubre la funcionalidad de navegación, búsqueda y filtrado de perfumes
 */@OptIn(ExperimentalCoroutinesApi::class)
class PerfumeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repositorio: RepositorioSuperfume
    private lateinit var viewModel: ViewModelPerfume

    private val perfumeEjemplo1 = Perfume(
        id = 1,
        name = "Chanel No. 5",
        brand = "Chanel",
        description = "Perfume clásico",
        price = 15000,
        stock = 10,
        category = "Floral",
        gender = "Mujer",
        size = "50ml",
        imageUri = "url1",
        isAvailable = true
    )

    private val perfumeEjemplo2 = Perfume(
        id = 2,
        name = "Sauvage",
        brand = "Dior",
        description = "Perfume masculino",
        price = 12000,
        stock = 5,
        category = "Amaderado",
        gender = "Hombre",
        size = "100ml",
        imageUri = "url2",
        isAvailable = true
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repositorio = mockk(relaxed = true)
        
        // Mock por defecto para cargar perfumes
        coEvery { repositorio.obtenerTodosLosPerfumesDisponibles() } returns flowOf(
            listOf(perfumeEjemplo1, perfumeEjemplo2)
        )
        
        viewModel = ViewModelPerfume(repositorio)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `cargarPerfumes debe actualizar la lista de perfumes correctamente`() = runTest {
        // Given
        val perfumesEsperados = listOf(perfumeEjemplo1, perfumeEjemplo2)
        coEvery { repositorio.obtenerTodosLosPerfumesDisponibles() } returns flowOf(perfumesEsperados)

        // When
        viewModel.cargarPerfumes()
        advanceUntilIdle()

        // Then
        assertEquals(perfumesEsperados, viewModel.perfumes.value)
        coVerify { repositorio.obtenerTodosLosPerfumesDisponibles() }
    }

    @Test
    fun `cargarPerfumes debe establecer estaCargando en false cuando termine`() = runTest {
        // When
        viewModel.cargarPerfumes()
        advanceUntilIdle()

        // Then
        assertFalse(viewModel.estaCargando.value)
    }

    @Test
    fun `cargarPerfumes debe manejar errores correctamente`() = runTest {
        // Given
        val mensajeError = "Error de red"
        coEvery { repositorio.obtenerTodosLosPerfumesDisponibles() } throws Exception(mensajeError)

        // When
        viewModel.cargarPerfumes()
        advanceUntilIdle()

        // Then
        val error = viewModel.mensajeError.value
        assertTrue(error?.contains("Error al cargar perfumes") == true)
    }

    @Test
    fun `buscarPerfumes debe actualizar consultaBusqueda y buscar en el repositorio`() = runTest {
        // Given
        val consulta = "Chanel"
        val resultados = listOf(perfumeEjemplo1)
        coEvery { repositorio.buscarPerfumes(consulta) } returns flowOf(resultados)

        // When
        viewModel.buscarPerfumes(consulta)
        advanceUntilIdle()

        // Then
        assertEquals(consulta, viewModel.consultaBusqueda.value)
        assertEquals(resultados, viewModel.perfumes.value)
        coVerify { repositorio.buscarPerfumes(consulta) }
    }

    @Test
    fun `filtrarPorCategoria debe actualizar categoriaSeleccionada y filtrar perfumes`() = runTest {
        // Given
        val categoria = "Floral"
        val resultados = listOf(perfumeEjemplo1)
        coEvery { repositorio.obtenerPerfumesPorCategoria(categoria) } returns flowOf(resultados)

        // When
        viewModel.filtrarPorCategoria(categoria)
        advanceUntilIdle()

        // Then
        assertEquals(categoria, viewModel.categoriaSeleccionada.value)
        assertEquals(resultados, viewModel.perfumes.value)
        coVerify { repositorio.obtenerPerfumesPorCategoria(categoria) }
    }

    @Test
    fun `filtrarPorGenero debe actualizar generoSeleccionado y filtrar perfumes`() = runTest {
        // Given
        val genero = "Mujer"
        val resultados = listOf(perfumeEjemplo1)
        coEvery { repositorio.obtenerPerfumesPorGenero(genero) } returns flowOf(resultados)

        // When
        viewModel.filtrarPorGenero(genero)
        advanceUntilIdle()

        // Then
        assertEquals(genero, viewModel.generoSeleccionado.value)
        assertEquals(resultados, viewModel.perfumes.value)
        coVerify { repositorio.obtenerPerfumesPorGenero(genero) }
    }

    @Test
    fun `limpiarError debe establecer mensajeError en null`() = runTest {
        // Given - forzar un error primero
        coEvery { repositorio.obtenerTodosLosPerfumesDisponibles() } throws Exception("Error")
        viewModel.cargarPerfumes()
        advanceUntilIdle()

        // When
        viewModel.limpiarError()

        // Then
        assertNull(viewModel.mensajeError.value)
    }
}
