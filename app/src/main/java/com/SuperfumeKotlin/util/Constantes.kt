package com.SuperfumeKotlin.util

/**
 * Constantes de la aplicación Superfume
 */
object Constantes {
    
    // Base de datos
    const val NOMBRE_BASE_DATOS = "superfume_database"
    const val VERSION_BASE_DATOS = 1
    
    // Tablas
    const val TABLA_PERFUMES = "perfumes"
    const val TABLA_USUARIOS = "users"
    const val TABLA_CARRITO = "cart_items"
    
    // Validaciones
    const val LONGITUD_MINIMA_CONTRASEÑA = 6
    const val LONGITUD_MAXIMA_CONTRASEÑA = 20
    const val LONGITUD_MINIMA_NOMBRE = 2
    const val LONGITUD_MAXIMA_NOMBRE = 50
    const val PRECIO_MAXIMO = 10000.0
    const val STOCK_MAXIMO = 1000
    
    // Categorías de perfumes
    val CATEGORIAS_PERFUMES = listOf(
        "Todos",
        "Frescos",
        "Florales", 
        "Orientales",
        "Amaderados",
        "Cítricos"
    )
    
    // Géneros
    val GENEROS_PERFUMES = listOf(
        "Todos",
        "Masculino",
        "Femenino",
        "Unisex"
    )
    
    // Tamaños disponibles
    val TAMAÑOS_PERFUMES = listOf(
        "30ml",
        "50ml", 
        "75ml",
        "100ml",
        "150ml"
    )
    
    // Rutas de navegación
    const val RUTA_LOGIN = "login"
    const val RUTA_REGISTRO = "register"
    const val RUTA_HOME = "home"
    const val RUTA_DETALLE_PERFUME = "perfume_detail/{perfumeId}"
    const val RUTA_CARRITO = "cart"
    const val RUTA_PERFIL = "profile"
    const val RUTA_AGREGAR_PERFUME = "add_perfume"
    const val RUTA_EDITAR_PERFIL = "edit_profile"
    
    // Colores del tema
    const val COLOR_PRIMARIO = 0xFF6B73FF
    const val COLOR_SECUNDARIO = 0xFF9B59B6
    const val COLOR_ERROR = 0xFFD32F2F
    const val COLOR_EXITO = 0xFF4CAF50
    const val COLOR_FONDO = 0xFFF8F9FA
}
