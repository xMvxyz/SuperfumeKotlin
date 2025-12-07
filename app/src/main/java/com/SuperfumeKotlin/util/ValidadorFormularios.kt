package com.SuperfumeKotlin.util

import android.util.Patterns
import com.SuperfumeKotlin.util.RecursosTexto

/**
 * Clase de utilidad para validaciones de formularios
 * Centraliza todas las validaciones de la aplicación
 */
object ValidadorFormularios {
    
    /**
     * Valida un email
     * @param email Email a validar
     * @return Resultado de la validación
     */
    fun validarEmail(email: String): ResultadoValidacion {
        return when {
            email.isEmpty() -> ResultadoValidacion(
                esValido = false,
                mensajeError = RecursosTexto.ERROR_EMAIL_INVALIDO
            )
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> ResultadoValidacion(
                esValido = false,
                mensajeError = RecursosTexto.ERROR_EMAIL_INVALIDO
            )
            email.length < 5 -> ResultadoValidacion(
                esValido = false,
                mensajeError = RecursosTexto.ERROR_EMAIL_INVALIDO
            )
            else -> ResultadoValidacion(esValido = true)
        }
    }
    
    /**
     * Valida una contraseña
     * @param contraseña Contraseña a validar
     * @return Resultado de la validación
     */
    fun validarContraseña(contraseña: String): ResultadoValidacion {
        return when {
            contraseña.isEmpty() -> ResultadoValidacion(
                esValido = false,
                mensajeError = RecursosTexto.ERROR_CONTRASEÑA_CORTA
            )
            contraseña.length < Constantes.LONGITUD_MINIMA_CONTRASEÑA -> ResultadoValidacion(
                esValido = false,
                mensajeError = RecursosTexto.ERROR_CONTRASEÑA_CORTA
            )
            contraseña.length > Constantes.LONGITUD_MAXIMA_CONTRASEÑA -> ResultadoValidacion(
                esValido = false,
                mensajeError = RecursosTexto.ERROR_CONTRASEÑA_LARGA
            )
            !contraseña.any { it.isDigit() } -> ResultadoValidacion(
                esValido = false,
                mensajeError = RecursosTexto.ERROR_CONTRASEÑA_NUMERO
            )
            !contraseña.any { it.isLetter() } -> ResultadoValidacion(
                esValido = false,
                mensajeError = RecursosTexto.ERROR_CONTRASEÑA_LETRA
            )
            else -> ResultadoValidacion(esValido = true)
        }
    }
    
    /**
     * Valida un nombre (nombre o apellido)
     * @param nombre Nombre a validar
     * @return Resultado de la validación
     */
    fun validarNombre(nombre: String): ResultadoValidacion {
        return when {
            nombre.isEmpty() -> ResultadoValidacion(
                esValido = false,
                mensajeError = RecursosTexto.ERROR_NOMBRE_OBLIGATORIO
            )
            nombre.length < Constantes.LONGITUD_MINIMA_NOMBRE -> ResultadoValidacion(
                esValido = false,
                mensajeError = RecursosTexto.ERROR_NOMBRE_CORTO
            )
            nombre.length > Constantes.LONGITUD_MAXIMA_NOMBRE -> ResultadoValidacion(
                esValido = false,
                mensajeError = RecursosTexto.ERROR_NOMBRE_LARGO
            )
            !nombre.all { it.isLetter() || it.isWhitespace() } -> ResultadoValidacion(
                esValido = false,
                mensajeError = RecursosTexto.ERROR_NOMBRE_SOLO_LETRAS
            )
            else -> ResultadoValidacion(esValido = true)
        }
    }
    
    /**
     * Valida un número de teléfono
     * @param telefono Teléfono a validar
     * @return Resultado de la validación
     */
    fun validarTelefono(telefono: String): ResultadoValidacion {
        return when {
            telefono.isEmpty() -> ResultadoValidacion(esValido = false, mensajeError = "El teléfono es obligatorio")
            !telefono.all { it.isDigit() } -> ResultadoValidacion(esValido = false, mensajeError = "El teléfono solo puede contener números")
            telefono.length < 8 -> ResultadoValidacion(
                esValido = false,
                mensajeError = "El teléfono debe tener al menos 8 dígitos"
            )
            telefono.length > 15 -> ResultadoValidacion(
                esValido = false,
                mensajeError = "El teléfono no puede tener más de 15 dígitos"
            )
            else -> ResultadoValidacion(esValido = true)
        }
    }

    /**
     * Valida una dirección
     * @param direccion Dirección a validar
     * @return Resultado de la validación
     */
    fun validarDireccion(direccion: String): ResultadoValidacion {
        return when {
            direccion.isEmpty() -> ResultadoValidacion(esValido = false, mensajeError = "La dirección es obligatoria")
            direccion.length < 5 -> ResultadoValidacion(esValido = false, mensajeError = "La dirección debe tener al menos 5 caracteres")
            else -> ResultadoValidacion(esValido = true)
        }
    }
    
    /**
     * Valida un precio
     * @param precio Precio a validar
     * @return Resultado de la validación
     */
    fun validarPrecio(precio: String): ResultadoValidacion {
        return when {
            precio.isEmpty() -> ResultadoValidacion(
                esValido = false,
                mensajeError = RecursosTexto.ERROR_PRECIO_INVALIDO
            )
            precio.toDoubleOrNull() == null -> ResultadoValidacion(
                esValido = false,
                mensajeError = RecursosTexto.ERROR_PRECIO_INVALIDO
            )
            precio.toDouble() <= 0 -> ResultadoValidacion(
                esValido = false,
                mensajeError = RecursosTexto.ERROR_PRECIO_CERO
            )
            precio.toDouble() > Constantes.PRECIO_MAXIMO -> ResultadoValidacion(
                esValido = false,
                mensajeError = RecursosTexto.ERROR_PRECIO_MAXIMO
            )
            else -> ResultadoValidacion(esValido = true)
        }
    }
    
    /**
     * Valida el stock
     * @param stock Stock a validar
     * @return Resultado de la validación
     */
    fun validarStock(stock: String): ResultadoValidacion {
        return when {
            stock.isEmpty() -> ResultadoValidacion(
                esValido = false,
                mensajeError = RecursosTexto.ERROR_STOCK_INVALIDO
            )
            stock.toIntOrNull() == null -> ResultadoValidacion(
                esValido = false,
                mensajeError = RecursosTexto.ERROR_STOCK_INVALIDO
            )
            stock.toInt() < 0 -> ResultadoValidacion(
                esValido = false,
                mensajeError = RecursosTexto.ERROR_STOCK_NEGATIVO
            )
            stock.toInt() > Constantes.STOCK_MAXIMO -> ResultadoValidacion(
                esValido = false,
                mensajeError = RecursosTexto.ERROR_STOCK_MAXIMO
            )
            else -> ResultadoValidacion(esValido = true)
        }
    }
}

/**
 * Clase de datos que representa el resultado de una validación
 * @property esValido Indica si la validación fue exitosa
 * @property mensajeError Mensaje de error si la validación falló
 */
data class ResultadoValidacion(
    val esValido: Boolean,
    val mensajeError: String? = null
)
