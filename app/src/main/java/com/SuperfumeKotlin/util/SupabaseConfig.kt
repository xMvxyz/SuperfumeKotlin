package com.SuperfumeKotlin.util

import android.content.Context
import java.util.Properties

/**
 * Gestor de configuración de Supabase
 * Lee las credenciales desde local.properties
 */
object SupabaseConfig {
    
    private var properties: Properties? = null
    
    /**
     * Inicializa la configuración leyendo local.properties
     * Llamar desde Application.onCreate()
     */
    fun init(context: Context) {
        try {
            properties = Properties().apply {
                context.assets.open("local.properties").use { inputStream ->
                    load(inputStream)
                }
            }
        } catch (e: Exception) {
            // Si no existe local.properties, usar valores por defecto o lanzar error
            e.printStackTrace()
            throw IllegalStateException(
                "No se encontró local.properties. " +
                "Copia local.properties.example a local.properties y configura tus credenciales de Supabase."
            )
        }
    }
    
    /**
     * URL del proyecto Supabase
     * Ejemplo: https://xxxxxxxxxxx.supabase.co
     */
    val supabaseUrl: String
        get() = properties?.getProperty("supabase.url")
            ?: throw IllegalStateException("supabase.url no configurado en local.properties")
    
    /**
     * Anon Key de Supabase (clave pública)
     * Segura para usar en el cliente móvil
     */
    val supabaseAnonKey: String
        get() = properties?.getProperty("supabase.anon.key")
            ?: throw IllegalStateException("supabase.anon.key no configurado en local.properties")
    
    /**
     * URL de la API REST de Supabase
     * Ejemplo: https://xxxxxxxxxxx.supabase.co/rest/v1
     */
    val supabaseApiUrl: String
        get() = properties?.getProperty("supabase.api.url")
            ?: "$supabaseUrl/rest/v1"
    
    /**
     * Host de la base de datos PostgreSQL
     * Ejemplo: db.xxxxxxxxxxx.supabase.co
     */
    val dbHost: String?
        get() = properties?.getProperty("supabase.db.host")
    
    /**
     * Puerto de PostgreSQL (por defecto 5432)
     */
    val dbPort: Int
        get() = properties?.getProperty("supabase.db.port")?.toIntOrNull() ?: 5432
    
    /**
     * Nombre de la base de datos (por defecto postgres)
     */
    val dbName: String
        get() = properties?.getProperty("supabase.db.name") ?: "postgres"
    
    /**
     * Usuario de PostgreSQL
     */
    val dbUser: String?
        get() = properties?.getProperty("supabase.db.user")
    
    /**
     * Contraseña de PostgreSQL
     */
    val dbPassword: String?
        get() = properties?.getProperty("supabase.db.password")
    
    /**
     * Verifica si la configuración está completa
     */
    fun isConfigured(): Boolean {
        return try {
            supabaseUrl.isNotEmpty() && supabaseAnonKey.isNotEmpty()
        } catch (e: Exception) {
            false
        }
    }
    
    /**
     * Obtiene la cadena de conexión JDBC para PostgreSQL
     * Solo si se configuraron las credenciales de DB
     */
    fun getJdbcUrl(): String? {
        return dbHost?.let {
            "jdbc:postgresql://$it:$dbPort/$dbName"
        }
    }
}
