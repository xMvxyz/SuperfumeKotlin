package com.SuperfumeKotlin.data.remote

import com.SuperfumeKotlin.data.model.Perfume
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val api: ApiService
) {
    suspend fun obtenerPerfumes() = api.getPerfumes()
}
