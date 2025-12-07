package com.SuperfumeKotlin.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.SuperfumeKotlin.data.model.ElementoCarrito
import com.SuperfumeKotlin.data.model.Perfume
import com.SuperfumeKotlin.data.repository.RepositorioSuperfume
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para la gestión del carrito de compras
 * Maneja la adición, eliminación y actualización de elementos del carrito
 */
@HiltViewModel
class ViewModelCarrito @Inject constructor(
    private val repositorio: RepositorioSuperfume
) : ViewModel() {
    
    private val _elementosCarrito = MutableStateFlow<List<ElementoCarrito>>(emptyList())
    val elementosCarrito: StateFlow<List<ElementoCarrito>> = _elementosCarrito.asStateFlow()
    
    private val _perfumesCarrito = MutableStateFlow<List<Perfume>>(emptyList())
    val perfumesCarrito: StateFlow<List<Perfume>> = _perfumesCarrito.asStateFlow()
    
    private val _precioTotal = MutableStateFlow(0.0)
    val precioTotal: StateFlow<Double> = _precioTotal.asStateFlow()
    
    private val _totalElementos = MutableStateFlow(0)
    val totalElementos: StateFlow<Int> = _totalElementos.asStateFlow()
    
    private val _estaCargando = MutableStateFlow(false)
    val estaCargando: StateFlow<Boolean> = _estaCargando.asStateFlow()
    
    private val _mensajeError = MutableStateFlow<String?>(null)
    val mensajeError: StateFlow<String?> = _mensajeError.asStateFlow()
    
    /**
     * Carga los elementos del carrito de un usuario
     * @param idUsuario ID del usuario
     */
    fun cargarElementosCarrito(idUsuario: Long) {
        viewModelScope.launch {
            _estaCargando.value = true
            try {
                repositorio.obtenerElementosCarritoPorUsuario(idUsuario).collect { elementos ->
                    _elementosCarrito.value = elementos
                    _totalElementos.value = elementos.sumOf { it.quantity }
                    
                    // Cargar detalles de perfumes para cada elemento del carrito
                    val perfumes = mutableListOf<Perfume>()
                    for (elemento in elementos) {
                        val perfume = repositorio.obtenerPerfumePorId(elemento.perfumeId)
                        if (perfume != null) {
                            perfumes.add(perfume)
                        }
                    }
                    _perfumesCarrito.value = perfumes
                    
                    // Calcular precio total
                    var total = 0.0
                    for (elemento in elementos) {
                        val perfume = perfumes.find { it.id == elemento.perfumeId }
                        if (perfume != null) {
                            total += perfume.price * elemento.quantity
                        }
                    }
                    _precioTotal.value = total
                    
                    _estaCargando.value = false
                }
            } catch (e: Exception) {
                _mensajeError.value = "Error al cargar el carrito: ${e.message}"
                _estaCargando.value = false
            }
        }
    }
    
    /**
     * Agrega un perfume al carrito
     * @param idUsuario ID del usuario
     * @param idPerfume ID del perfume
     * @param cantidad Cantidad a agregar
     */
    fun agregarAlCarrito(idUsuario: Long, idPerfume: Long, cantidad: Int = 1) {
        viewModelScope.launch {
            try {
                val elementoExistente = repositorio.obtenerElementoCarrito(idUsuario, idPerfume)
                if (elementoExistente != null) {
                    val elementoActualizado = elementoExistente.copy(quantity = elementoExistente.quantity + cantidad)
                    repositorio.actualizarElementoCarrito(elementoActualizado)
                } else {
                    val nuevoElemento = ElementoCarrito(
                        userId = idUsuario,
                        perfumeId = idPerfume,
                        quantity = cantidad
                    )
                    repositorio.agregarAlCarrito(nuevoElemento)
                }
                cargarElementosCarrito(idUsuario)
            } catch (e: Exception) {
                _mensajeError.value = "Error al agregar al carrito: ${e.message}"
            }
        }
    }
    
    /**
     * Actualiza la cantidad de un elemento del carrito
     * @param idUsuario ID del usuario
     * @param idPerfume ID del perfume
     * @param nuevaCantidad Nueva cantidad
     */
    fun actualizarCantidadElemento(idUsuario: Long, idPerfume: Long, nuevaCantidad: Int) {
        viewModelScope.launch {
            try {
                if (nuevaCantidad <= 0) {
                    eliminarDelCarrito(idUsuario, idPerfume)
                } else {
                    val elementoExistente = repositorio.obtenerElementoCarrito(idUsuario, idPerfume)
                    if (elementoExistente != null) {
                        val elementoActualizado = elementoExistente.copy(quantity = nuevaCantidad)
                        repositorio.actualizarElementoCarrito(elementoActualizado)
                        cargarElementosCarrito(idUsuario)
                    }
                }
            } catch (e: Exception) {
                _mensajeError.value = "Error al actualizar cantidad: ${e.message}"
            }
        }
    }
    
    /**
     * Elimina un perfume del carrito
     * @param idUsuario ID del usuario
     * @param idPerfume ID del perfume a eliminar
     */
    fun eliminarDelCarrito(idUsuario: Long, idPerfume: Long) {
        viewModelScope.launch {
            try {
                repositorio.eliminarDelCarrito(idUsuario, idPerfume)
                cargarElementosCarrito(idUsuario)
            } catch (e: Exception) {
                _mensajeError.value = "Error al eliminar del carrito: ${e.message}"
            }
        }
    }
    
    /**
     * Vacía completamente el carrito
     * @param idUsuario ID del usuario
     */
    fun vaciarCarrito(idUsuario: Long) {
        viewModelScope.launch {
            try {
                repositorio.vaciarCarritoPorUsuario(idUsuario)
                cargarElementosCarrito(idUsuario)
            } catch (e: Exception) {
                _mensajeError.value = "Error al vaciar el carrito: ${e.message}"
            }
        }
    }
    
    /**
     * Limpia el mensaje de error
     */
    fun limpiarError() {
        _mensajeError.value = null
    }
}
