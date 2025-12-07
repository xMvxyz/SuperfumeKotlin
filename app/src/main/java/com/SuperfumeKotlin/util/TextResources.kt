package com.SuperfumeKotlin.util

/**
 * Recursos de texto en español para la aplicación Superfume
 */
object TextResources {
    
    // Títulos de pantallas
    const val APP_TITLE = "Superfume"
    const val LOGIN_TITLE = "Iniciar Sesión"
    const val REGISTER_TITLE = "Crear Cuenta"
    const val HOME_TITLE = "Catálogo de Perfumes"
    const val DETAIL_TITLE = "Detalles del Perfume"
    const val CART_TITLE = "Carrito de Compras"
    const val PROFILE_TITLE = "Mi Perfil"
    const val ADD_PERFUME_TITLE = "Agregar Perfume"
    
    // Subtítulos
    const val LOGIN_SUBTITLE = "Tu tienda de perfumes favorita"
    const val REGISTER_SUBTITLE = "Únete a nuestra comunidad"
    const val EMPTY_CART_SUBTITLE = "Tu carrito está vacío"
    const val NOT_LOGGED_IN_SUBTITLE = "No has iniciado sesión"
    
    // Campos de formulario
    const val FIELD_EMAIL = "Email"
    const val FIELD_PASSWORD = "Contraseña"
    const val FIELD_FIRST_NAME = "Nombre"
    const val FIELD_LAST_NAME = "Apellido"
    const val FIELD_PHONE = "Teléfono (Opcional)"
    const val FIELD_ADDRESS = "Dirección (Opcional)"
    const val FIELD_PERFUME_NAME = "Nombre del Perfume"
    const val FIELD_BRAND = "Marca"
    const val FIELD_PRICE = "Precio ($)"
    const val FIELD_STOCK = "Stock"
    const val FIELD_CATEGORY = "Categoría"
    const val FIELD_SIZE = "Tamaño"
    const val FIELD_GENDER = "Género"
    const val FIELD_DESCRIPTION = "Descripción"
    const val FIELD_QUANTITY = "Cantidad"
    
    // Botones
    const val BUTTON_LOGIN = "Iniciar Sesión"
    const val BUTTON_REGISTER = "Crear Cuenta"
    const val BUTTON_ADD_TO_CART = "Agregar al Carrito"
    const val BUTTON_CHECKOUT = "Proceder al Pago"
    const val BUTTON_EMPTY_CART = "Vaciar"
    const val BUTTON_SIGN_OUT = "Cerrar Sesión"
    const val BUTTON_EDIT_PROFILE = "Editar Perfil"
    const val BUTTON_ADD_PERFUME = "Agregar Perfume"
    const val BUTTON_GO_TO_STORE = "Ir a la tienda"
    const val BUTTON_SIGN_IN = "Iniciar Sesión"
    
    // Enlaces
    const val LINK_REGISTER = "¿No tienes cuenta? Regístrate"
    const val LINK_LOGIN = "¿Ya tienes cuenta? Inicia Sesión"
    
    // Mensajes de error
    const val ERROR_INVALID_EMAIL = "Email inválido"
    const val ERROR_PASSWORD_TOO_SHORT = "La contraseña debe tener al menos 6 caracteres"
    const val ERROR_PASSWORD_TOO_LONG = "La contraseña no puede tener más de 20 caracteres"
    const val ERROR_PASSWORD_NEEDS_NUMBER = "La contraseña debe contener al menos un número"
    const val ERROR_PASSWORD_NEEDS_LETTER = "La contraseña debe contener al menos una letra"
    const val ERROR_NAME_REQUIRED = "El nombre es obligatorio"
    const val ERROR_NAME_TOO_SHORT = "El nombre debe tener al menos 2 caracteres"
    const val ERROR_NAME_TOO_LONG = "El nombre no puede tener más de 50 caracteres"
    const val ERROR_NAME_LETTERS_ONLY = "El nombre solo puede contener letras y espacios"
    const val ERROR_INVALID_PRICE = "El precio debe ser un número válido"
    const val ERROR_PRICE_ZERO = "El precio debe ser mayor a 0"
    const val ERROR_MAX_PRICE = "El precio no puede ser mayor a $10,000"
    const val ERROR_INVALID_STOCK = "El stock debe ser un número entero"
    const val ERROR_NEGATIVE_STOCK = "El stock no puede ser negativo"
    const val ERROR_MAX_STOCK = "El stock no puede ser mayor a 1000"
    const val ERROR_INVALID_CREDENTIALS = "Credenciales inválidas"
    const val ERROR_EMAIL_ALREADY_REGISTERED = "El email ya está registrado"
    const val ERROR_REQUIRED_FIELDS = "Nombre y apellido son obligatorios"
    
    // Mensajes de éxito
    const val SUCCESS_ADDED_TO_CART = "¡Agregado al carrito!"
    const val SUCCESS_ORDER_PLACED = "¡Pedido realizado con éxito!"
    const val SUCCESS_PERFUME_ADDED = "¡Perfume agregado exitosamente!"
    const val SUCCESS_VALID_FIELD = "Campo válido"
    
    // Mensajes informativos
    const val INFO_LOADING = "Cargando..."
    const val INFO_SEARCH_PERFUMES = "Buscar perfumes..."
    const val INFO_OUT_OF_STOCK = "Este perfume está agotado"
    const val INFO_ADD_IMAGE = "Agregar\nImagen"
    const val INFO_SELECT_IMAGE = "Seleccionar imagen"
    const val INFO_HOW_TO_ADD_IMAGE = "¿Cómo quieres agregar la imagen?"
    const val INFO_GALLERY = "Galería"
    const val INFO_CAMERA = "Cámara"
    const val INFO_CONFIRM_SIGN_OUT = "¿Estás seguro de que quieres cerrar sesión?"
    const val INFO_YES_SIGN_OUT = "Sí, cerrar sesión"
    const val INFO_CANCEL = "Cancelar"
    
    // Etiquetas
    const val LABEL_CATEGORIES = "Categorías"
    const val LABEL_GENDER = "Género"
    const val LABEL_PERSONAL_INFO = "Información Personal"
    const val LABEL_ACTIONS = "Acciones"
    const val LABEL_TOTAL_PRODUCTS = "Total de productos:"
    const val LABEL_TOTAL_TO_PAY = "Total a pagar:"
    const val LABEL_DESCRIPTION = "Descripción"
    
    // Placeholders
    const val PLACEHOLDER_SEARCH = "Buscar perfumes..."
    const val PLACEHOLDER_ADD_PERFUME = "Agrega algunos perfumes para comenzar"
    const val PLACEHOLDER_SIGN_IN_CART = "Inicia sesión para ver tu carrito"
    
    // Iconos (descripciones)
    const val ICON_SEARCH = "Buscar"
    const val ICON_CART = "Carrito"
    const val ICON_PROFILE = "Perfil"
    const val ICON_ADD_PERFUME = "Agregar Perfume"
    const val ICON_BACK = "Volver"
    const val ICON_DELETE = "Eliminar"
    const val ICON_DECREASE = "Disminuir"
    const val ICON_INCREASE = "Aumentar"
    const val ICON_SHOW_PASSWORD = "Mostrar contraseña"
    const val ICON_HIDE_PASSWORD = "Ocultar contraseña"
    const val ICON_CLEAR = "Limpiar"
    const val ICON_PROFILE_PHOTO = "Foto de perfil"
    const val ICON_PERFUME_IMAGE = "Imagen del perfume"
    const val ICON_SELECTED_IMAGE = "Imagen seleccionada"
}
