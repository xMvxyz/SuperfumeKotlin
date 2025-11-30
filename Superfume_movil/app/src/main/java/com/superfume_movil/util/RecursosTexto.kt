package com.superfume_movil.util

/**
 * Recursos de texto en español para la aplicación Superfume
 */
object RecursosTexto {
    
    // Títulos de pantallas
    const val TITULO_APP = "Superfume"
    const val TITULO_LOGIN = "Iniciar Sesión"
    const val TITULO_REGISTRO = "Crear Cuenta"
    const val TITULO_HOME = "Catálogo de Perfumes"
    const val TITULO_DETALLE = "Detalles del Perfume"
    const val TITULO_CARRITO = "Carrito de Compras"
    const val TITULO_PERFIL = "Mi Perfil"
    const val TITULO_AGREGAR_PERFUME = "Agregar Perfume"
    
    // Subtítulos
    const val SUBTITULO_LOGIN = "Tu tienda de perfumes favorita"
    const val SUBTITULO_REGISTRO = "Únete a nuestra comunidad"
    const val SUBTITULO_CARRITO_VACIO = "Tu carrito está vacío"
    const val SUBTITULO_PERFIL_NO_LOGUEADO = "No has iniciado sesión"
    
    // Campos de formulario
    const val CAMPO_EMAIL = "Email"
    const val CAMPO_CONTRASEÑA = "Contraseña"
    const val CAMPO_NOMBRE = "Nombre"
    const val CAMPO_APELLIDO = "Apellido"
    const val CAMPO_TELEFONO = "Teléfono (Opcional)"
    const val CAMPO_DIRECCION = "Dirección (Opcional)"
    const val CAMPO_NOMBRE_PERFUME = "Nombre del Perfume"
    const val CAMPO_MARCA = "Marca"
    const val CAMPO_PRECIO = "Precio ($)"
    const val CAMPO_STOCK = "Stock"
    const val CAMPO_CATEGORIA = "Categoría"
    const val CAMPO_TAMAÑO = "Tamaño"
    const val CAMPO_GENERO = "Género"
    const val CAMPO_DESCRIPCION = "Descripción"
    const val CAMPO_CANTIDAD = "Cantidad"
    
    // Botones
    const val BOTON_LOGIN = "Iniciar Sesión"
    const val BOTON_REGISTRO = "Crear Cuenta"
    const val BOTON_AGREGAR_CARRITO = "Agregar al Carrito"
    const val BOTON_PROCESAR_PAGO = "Proceder al Pago"
    const val BOTON_VACIAR_CARRITO = "Vaciar"
    const val BOTON_CERRAR_SESION = "Cerrar Sesión"
    const val BOTON_EDITAR_PERFIL = "Editar Perfil"
    const val BOTON_AGREGAR_PERFUME = "Agregar Perfume"
    const val BOTON_IR_TIENDA = "Ir a la tienda"
    const val BOTON_INICIAR_SESION = "Iniciar Sesión"
    
    // Enlaces
    const val ENLACE_REGISTRO = "¿No tienes cuenta? Regístrate"
    const val ENLACE_LOGIN = "¿Ya tienes cuenta? Inicia Sesión"
    
    // Mensajes de error
    const val ERROR_EMAIL_INVALIDO = "Email inválido"
    const val ERROR_CONTRASEÑA_CORTA = "La contraseña debe tener al menos 6 caracteres"
    const val ERROR_CONTRASEÑA_LARGA = "La contraseña no puede tener más de 20 caracteres"
    const val ERROR_CONTRASEÑA_NUMERO = "La contraseña debe contener al menos un número"
    const val ERROR_CONTRASEÑA_LETRA = "La contraseña debe contener al menos una letra"
    const val ERROR_NOMBRE_OBLIGATORIO = "El nombre es obligatorio"
    const val ERROR_NOMBRE_CORTO = "El nombre debe tener al menos 2 caracteres"
    const val ERROR_NOMBRE_LARGO = "El nombre no puede tener más de 50 caracteres"
    const val ERROR_NOMBRE_SOLO_LETRAS = "El nombre solo puede contener letras y espacios"
    const val ERROR_PRECIO_INVALIDO = "El precio debe ser un número válido"
    const val ERROR_PRECIO_CERO = "El precio debe ser mayor a 0"
    const val ERROR_PRECIO_MAXIMO = "El precio no puede ser mayor a $10,000"
    const val ERROR_STOCK_INVALIDO = "El stock debe ser un número entero"
    const val ERROR_STOCK_NEGATIVO = "El stock no puede ser negativo"
    const val ERROR_STOCK_MAXIMO = "El stock no puede ser mayor a 1000"
    const val ERROR_CREDENCIALES_INVALIDAS = "Credenciales inválidas"
    const val ERROR_EMAIL_REGISTRADO = "El email ya está registrado"
    const val ERROR_CAMPOS_OBLIGATORIOS = "Nombre y apellido son obligatorios"
    
    // Mensajes de éxito
    const val EXITO_AGREGADO_CARRITO = "¡Agregado al carrito!"
    const val EXITO_PEDIDO_REALIZADO = "¡Pedido realizado con éxito!"
    const val EXITO_PERFUME_AGREGADO = "¡Perfume agregado exitosamente!"
    const val EXITO_CAMPO_VALIDO = "Campo válido"
    
    // Mensajes informativos
    const val INFO_CARGANDO = "Cargando..."
    const val INFO_BUSCAR_PERFUMES = "Buscar perfumes..."
    const val INFO_PERFUME_AGOTADO = "Este perfume está agotado"
    const val INFO_AGREGAR_IMAGEN = "Agregar\nImagen"
    const val INFO_SELECCIONAR_IMAGEN = "Seleccionar imagen"
    const val INFO_COMO_AGREGAR_IMAGEN = "¿Cómo quieres agregar la imagen?"
    const val INFO_GALERIA = "Galería"
    const val INFO_CAMARA = "Cámara"
    const val INFO_CONFIRMAR_CERRAR_SESION = "¿Estás seguro de que quieres cerrar sesión?"
    const val INFO_SI_CERRAR_SESION = "Sí, cerrar sesión"
    const val INFO_CANCELAR = "Cancelar"
    
    // Etiquetas
    const val ETIQUETA_CATEGORIAS = "Categorías"
    const val ETIQUETA_GENERO = "Género"
    const val ETIQUETA_INFORMACION_PERSONAL = "Información Personal"
    const val ETIQUETA_ACCIONES = "Acciones"
    const val ETIQUETA_TOTAL_PRODUCTOS = "Total de productos:"
    const val ETIQUETA_TOTAL_PAGAR = "Total a pagar:"
    const val ETIQUETA_DESCRIPCION = "Descripción"
    
    // Placeholders
    const val PLACEHOLDER_BUSCAR = "Buscar perfumes..."
    const val PLACEHOLDER_AGREGAR_PERFUME = "Agrega algunos perfumes para comenzar"
    const val PLACEHOLDER_INICIAR_SESION_CARRITO = "Inicia sesión para ver tu carrito"
    
    // Iconos (descripciones)
    const val ICONO_BUSCAR = "Buscar"
    const val ICONO_CARRITO = "Carrito"
    const val ICONO_PERFIL = "Perfil"
    const val ICONO_AGREGAR_PERFUME = "Agregar Perfume"
    const val ICONO_VOLVER = "Volver"
    const val ICONO_ELIMINAR = "Eliminar"
    const val ICONO_DISMINUIR = "Disminuir"
    const val ICONO_AUMENTAR = "Aumentar"
    const val ICONO_MOSTRAR_CONTRASEÑA = "Mostrar contraseña"
    const val ICONO_OCULTAR_CONTRASEÑA = "Ocultar contraseña"
    const val ICONO_LIMPIAR = "Limpiar"
    const val ICONO_FOTO_PERFIL = "Foto de perfil"
    const val ICONO_IMAGEN_PERFUME = "Imagen del perfume"
    const val ICONO_IMAGEN_SELECCIONADA = "Imagen seleccionada"
}
