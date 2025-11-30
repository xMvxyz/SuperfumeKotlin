package com.superfume_movil

import android.app.Application
import com.superfume_movil.data.InicializadorDatos
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * Clase principal de la aplicación Superfume
 * Configura Hilt para inyección de dependencias e inicializa los datos de ejemplo
 */
@HiltAndroidApp
class AplicacionSuperfume : Application() {
    
    @Inject
    lateinit var inicializadorDatos: InicializadorDatos
    
    override fun onCreate() {
        super.onCreate()
        // Inicializar datos de ejemplo
        inicializadorDatos.inicializarDatos()
    }
}
