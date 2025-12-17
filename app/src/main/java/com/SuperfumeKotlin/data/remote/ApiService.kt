package com.SuperfumeKotlin.data.remote

import com.SuperfumeKotlin.data.model.dto.request.*
import com.SuperfumeKotlin.data.model.dto.response.*
import retrofit2.Response
import retrofit2.http.*

/**
 * Servicio API para comunicación con el backend Spring Boot
 */
interface ApiService {
    
    // ==================== AUTH ENDPOINTS ====================
    
    @POST("/api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
    
    @POST("/api/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<LoginResponse>
    
    // ==================== PERFUME ENDPOINTS ====================
    
    @GET("/api/perfumes")
    suspend fun getPerfumes(): Response<List<PerfumeResponse>>
    
    @GET("/api/perfumes/{id}")
    suspend fun getPerfume(@Path("id") id: Int): Response<PerfumeResponse>
    
    @POST("/api/perfumes")
    suspend fun createPerfume(@Body perfume: PerfumeResponse): Response<PerfumeResponse>
    
    @PUT("/api/perfumes/{id}")
    suspend fun updatePerfume(@Path("id") id: Int, @Body perfume: PerfumeResponse): Response<PerfumeResponse>
    
    @DELETE("/api/perfumes/{id}")
    suspend fun deletePerfume(@Path("id") id: Int): Response<Unit>
    
    // ==================== USUARIO ENDPOINTS ====================
    
    @GET("/api/usuario")
    suspend fun getUsuarios(): Response<List<UsuarioResponse>>
    
    @GET("/api/usuario/{id}")
    suspend fun getUsuario(@Path("id") id: Int): Response<UsuarioResponse>
    
    @PUT("/api/usuario/{id}")
    suspend fun updateUsuario(@Path("id") id: Int, @Body request: UpdateUserRequest): Response<UsuarioResponse>
    
    @PUT("/api/usuario/{id}/rol")
    suspend fun changeUserRole(@Path("id") id: Int, @Body request: ChangeRoleRequest): Response<UsuarioResponse>
    
    @DELETE("/api/usuario/{id}")
    suspend fun deleteUsuario(@Path("id") id: Int): Response<Unit>
    
    // ==================== CARRITO ENDPOINTS ====================
    
    @GET("/api/carrito/usuario/{usuarioId}")
    suspend fun getCarritoActivo(@Path("usuarioId") usuarioId: Int): Response<CarritoResponse>
    
    @GET("/api/carrito/{id}")
    suspend fun getCarrito(@Path("id") id: Int): Response<CarritoResponse>
    
    @POST("/api/carrito/{carritoId}/items")
    suspend fun agregarItemCarrito(
        @Path("carritoId") carritoId: Int,
        @Body item: CarritoItemRequest
    ): Response<CarritoResponse>
    
    @PUT("/api/carrito/items/{itemId}")
    suspend fun actualizarCantidadItem(
        @Path("itemId") itemId: Int,
        @Query("cantidad") cantidad: Int
    ): Response<CarritoResponse>
    
    @DELETE("/api/carrito/items/{itemId}")
    suspend fun eliminarItemCarrito(@Path("itemId") itemId: Int): Response<Unit>
    
    @DELETE("/api/carrito/{carritoId}/vaciar")
    suspend fun vaciarCarrito(@Path("carritoId") carritoId: Int): Response<Unit>
    
    // ==================== PEDIDO ENDPOINTS ====================
    
    @GET("/api/pedido")
    suspend fun getPedidos(): Response<List<PedidoResponse>>
    
    @GET("/api/pedido/{id}")
    suspend fun getPedido(@Path("id") id: Int): Response<PedidoResponse>
    
    @GET("/api/pedido/usuario/{usuarioId}")
    suspend fun getPedidosByUsuario(@Path("usuarioId") usuarioId: Int): Response<List<PedidoResponse>>
    
    @GET("/api/pedido/estado/{estado}")
    suspend fun getPedidosByEstado(@Path("estado") estado: String): Response<List<PedidoResponse>>
    
    @POST("/api/pedido")
    suspend fun crearPedido(@Body request: PedidoRequest): Response<PedidoResponse>
    
    @PATCH("/api/pedido/{id}/estado")
    suspend fun actualizarEstadoPedido(
        @Path("id") id: Int,
        @Query("estado") estado: String
    ): Response<PedidoResponse>
    
    @DELETE("/api/pedido/{id}")
    suspend fun deletePedido(@Path("id") id: Int): Response<Unit>
    
    // ==================== PAGO ENDPOINTS ====================
    
    @GET("/api/pago")
    suspend fun getPagos(): Response<List<PagoResponse>>
    
    @GET("/api/pago/{id}")
    suspend fun getPago(@Path("id") id: Int): Response<PagoResponse>
    
    @GET("/api/pago/pedido/{pedidoId}")
    suspend fun getPagosByPedido(@Path("pedidoId") pedidoId: Int): Response<List<PagoResponse>>
    
    @GET("/api/pago/estado/{estado}")
    suspend fun getPagosByEstado(@Path("estado") estado: String): Response<List<PagoResponse>>
    
    @POST("/api/pago")
    suspend fun crearPago(@Body request: PagoRequest): Response<PagoResponse>
    
    @PATCH("/api/pago/{id}/estado")
    suspend fun actualizarEstadoPago(
        @Path("id") id: Int,
        @Query("estado") estado: String,
        @Query("transaccionId") transaccionId: String?
    ): Response<PagoResponse>
    
    @DELETE("/api/pago/{id}")
    suspend fun deletePago(@Path("id") id: Int): Response<Unit>
}
