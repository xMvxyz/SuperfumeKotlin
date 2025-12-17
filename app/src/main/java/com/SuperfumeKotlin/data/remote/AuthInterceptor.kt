package com.SuperfumeKotlin.data.remote

import com.SuperfumeKotlin.util.TokenManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * Interceptor para agregar el token JWT a todas las peticiones
 */
class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {
    
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        
        // Si no hay token, continuar sin modificar la petición
        val token = tokenManager.getToken()
        if (token.isNullOrEmpty()) {
            return chain.proceed(originalRequest)
        }
        
        // Agregar el token al header Authorization
        val newRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()
        
        return chain.proceed(newRequest)
    }
}
