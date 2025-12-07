package com.superfume_movil.data.remote

import com.superfume_movil.data.model.Perfume
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val api: ApiService
) {
    suspend fun obtenerPerfumes(): List<Perfume> = api.getPerfumes()
}
