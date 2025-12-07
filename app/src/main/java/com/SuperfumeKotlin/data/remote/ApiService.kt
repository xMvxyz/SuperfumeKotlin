package com.SuperfumeKotlin.data.remote

import com.SuperfumeKotlin.data.model.Perfume
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/Perfume")
    suspend fun getPerfumes(): List<Perfume>

    @GET("/Perfume/{id}")
    suspend fun getPerfume(@Path("id") id: Long): Perfume
}
