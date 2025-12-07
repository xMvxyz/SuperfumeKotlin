package com.SuperfumeKotlin.util

/**
 * Constantes de la aplicación Superfume
 */
object Constants {
    
    // Base de datos
    const val DATABASE_NAME = "superfume_database"
    const val DATABASE_VERSION = 1
    
    // Tablas
    const val TABLE_PERFUMES = "perfumes"
    const val TABLE_USERS = "users"
    const val TABLE_CART = "cart_items"
    
    // Validaciones
    const val MIN_PASSWORD_LENGTH = 6
    const val MAX_PASSWORD_LENGTH = 20
    const val MIN_NAME_LENGTH = 2
    const val MAX_NAME_LENGTH = 50
    const val MAX_PRICE = 10000
    const val MAX_STOCK = 1000
    
    // Categorías de perfumes
    val PERFUME_CATEGORIES = listOf(
        "Todos",
        "Frescos",
        "Florales", 
        "Orientales",
        "Amaderados",
        "Cítricos"
    )
    
    // Géneros
    val PERFUME_GENDERS = listOf(
        "Todos",
        "Masculino",
        "Femenino",
        "Unisex"
    )
    
    // Tamaños disponibles
    val PERFUME_SIZES = listOf(
        "30ml",
        "50ml", 
        "75ml",
        "100ml",
        "150ml"
    )
    
    // Rutas de navegación
    const val ROUTE_LOGIN = "login"
    const val ROUTE_REGISTER = "register"
    const val ROUTE_HOME = "home"
    const val ROUTE_PERFUME_DETAIL = "perfume_detail/{perfumeId}"
    const val ROUTE_CART = "cart"
    const val ROUTE_PROFILE = "profile"
    const val ROUTE_ADD_PERFUME = "add_perfume"
    const val ROUTE_EDIT_PROFILE = "edit_profile"
    
    // Colores del tema
    const val COLOR_PRIMARY = 0xFF6B73FF
    const val COLOR_SECONDARY = 0xFF9B59B6
    const val COLOR_ERROR = 0xFFD32F2F
    const val COLOR_SUCCESS = 0xFF4CAF50
    const val COLOR_BACKGROUND = 0xFFF8F9FA
}
