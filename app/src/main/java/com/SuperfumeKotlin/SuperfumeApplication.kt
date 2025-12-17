package com.SuperfumeKotlin

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Clase principal de la aplicación Superfume
 * Configura Hilt para inyección de dependencias
 */
@HiltAndroidApp
class SuperfumeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
