package com.SuperfumeKotlin.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.SuperfumeKotlin.data.model.Perfume
import com.SuperfumeKotlin.data.repository.RepositorioSuperfume
import com.SuperfumeKotlin.util.Constantes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para la gestión de perfumes
 * Maneja la carga, búsqueda y filtrado de perfumes
 */
@HiltViewModel
class ViewModelPerfume @Inject constructor(
    private val repositorio: RepositorioSuperfume
) : ViewModel() {
    
    private val _perfumes = MutableStateFlow<List<Perfume>>(emptyList())
    val perfumes: StateFlow<List<Perfume>> = _perfumes.asStateFlow()
    
    private val _consultaBusqueda = MutableStateFlow("")
    val consultaBusqueda: StateFlow<String> = _consultaBusqueda.asStateFlow()
    
    private val _categoriaSeleccionada = MutableStateFlow(Constantes.CATEGORIAS_PERFUMES[0])
    val categoriaSeleccionada: StateFlow<String> = _categoriaSeleccionada.asStateFlow()
    
    private val _generoSeleccionado = MutableStateFlow(Constantes.GENEROS_PERFUMES[0])
    val generoSeleccionado: StateFlow<String> = _generoSeleccionado.asStateFlow()
    
    private val _estaCargando = MutableStateFlow(false)
    val estaCargando: StateFlow<Boolean> = _estaCargando.asStateFlow()
    
    private val _mensajeError = MutableStateFlow<String?>(null)
    val mensajeError: StateFlow<String?> = _mensajeError.asStateFlow()
    
    init {
        cargarPerfumes()
    }
    
    /**
     * Carga todos los perfumes disponibles
     */
    fun cargarPerfumes() {
        viewModelScope.launch {
            _estaCargando.value = true
            try {
                repositorio.obtenerTodosLosPerfumesDisponibles().collect { listaPerfumes ->
                    _perfumes.value = listaPerfumes
                    _estaCargando.value = false
                }
            } catch (e: Exception) {
                _mensajeError.value = "Error al cargar perfumes: ${e.message}"
                _estaCargando.value = false
            }
        }
    }
    
    /**
     * Busca perfumes por nombre o marca
     * @param consulta Texto de búsqueda
     */
    fun buscarPerfumes(consulta: String) {
        _consultaBusqueda.value = consulta
        viewModelScope.launch {
            _estaCargando.value = true
            try {
                if (consulta.isBlank()) {
                    cargarPerfumes()
                } else {
                    repositorio.buscarPerfumes(consulta).collect { listaPerfumes ->
                        _perfumes.value = listaPerfumes
                        _estaCargando.value = false
                    }
                }
            } catch (e: Exception) {
                _mensajeError.value = "Error en la búsqueda: ${e.message}"
                _estaCargando.value = false
            }
        }
    }
    
    /**
     * Filtra perfumes por categoría
     * @param categoria Categoría a filtrar
     */
    fun filtrarPorCategoria(categoria: String) {
        _categoriaSeleccionada.value = categoria
        viewModelScope.launch {
            _estaCargando.value = true
            try {
                if (categoria == Constantes.CATEGORIAS_PERFUMES[0]) {
                    cargarPerfumes()
                } else {
                    repositorio.obtenerPerfumesPorCategoria(categoria).collect { listaPerfumes ->
                        _perfumes.value = listaPerfumes
                        _estaCargando.value = false
                    }
                }
            } catch (e: Exception) {
                _mensajeError.value = "Error al filtrar por categoría: ${e.message}"
                _estaCargando.value = false
            }
        }
    }
    
    /**
     * Filtra perfumes por género
     * @param genero Género a filtrar
     */
    fun filtrarPorGenero(genero: String) {
        _generoSeleccionado.value = genero
        viewModelScope.launch {
            _estaCargando.value = true
            try {
                if (genero == Constantes.GENEROS_PERFUMES[0]) {
                    cargarPerfumes()
                } else {
                    repositorio.obtenerPerfumesPorGenero(genero).collect { listaPerfumes ->
                        _perfumes.value = listaPerfumes
                        _estaCargando.value = false
                    }
                }
            } catch (e: Exception) {
                _mensajeError.value = "Error al filtrar por género: ${e.message}"
                _estaCargando.value = false
            }
        }
    }
    
    /**
     * Limpia el mensaje de error
     */
    fun limpiarError() {
        _mensajeError.value = null
    }
    
    /**
     * Agrega un nuevo perfume
     * @param perfume Perfume a agregar
     */
    fun agregarPerfume(perfume: Perfume) {
        viewModelScope.launch {
            _estaCargando.value = true
            try {
                repositorio.insertarPerfume(perfume)
                cargarPerfumes()
                _mensajeError.value = null
            } catch (e: Exception) {
                _mensajeError.value = "Error al agregar perfume: ${e.message}"
            } finally {
                _estaCargando.value = false
            }
        }
    }
}
