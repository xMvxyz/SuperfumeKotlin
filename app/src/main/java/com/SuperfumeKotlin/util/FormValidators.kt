package com.SuperfumeKotlin.util

import android.util.Patterns

/**
 * Clase de utilidad para validaciones de formularios
 * Centraliza todas las validaciones de la aplicación
 */
object FormValidators {
    
    /**
     * Valida un email
     * @param email Email a validar
     * @return Resultado de la validación
     */
    fun validateEmail(email: String): ValidationResult {
        return when {
            email.isEmpty() -> ValidationResult(
                isValid = false,
                errorMessage = TextResources.ERROR_INVALID_EMAIL
            )
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> ValidationResult(
                isValid = false,
                errorMessage = TextResources.ERROR_INVALID_EMAIL
            )
            email.length < 5 -> ValidationResult(
                isValid = false,
                errorMessage = TextResources.ERROR_INVALID_EMAIL
            )
            else -> ValidationResult(isValid = true)
        }
    }
    
    /**
     * Valida una contraseña
     * @param password Contraseña a validar
     * @return Resultado de la validación
     */
    fun validatePassword(password: String): ValidationResult {
        return when {
            password.isEmpty() -> ValidationResult(
                isValid = false,
                errorMessage = TextResources.ERROR_PASSWORD_TOO_SHORT
            )
            password.length < Constants.MIN_PASSWORD_LENGTH -> ValidationResult(
                isValid = false,
                errorMessage = TextResources.ERROR_PASSWORD_TOO_SHORT
            )
            password.length > Constants.MAX_PASSWORD_LENGTH -> ValidationResult(
                isValid = false,
                errorMessage = TextResources.ERROR_PASSWORD_TOO_LONG
            )
            !password.any { it.isDigit() } -> ValidationResult(
                isValid = false,
                errorMessage = TextResources.ERROR_PASSWORD_NEEDS_NUMBER
            )
            !password.any { it.isLetter() } -> ValidationResult(
                isValid = false,
                errorMessage = TextResources.ERROR_PASSWORD_NEEDS_LETTER
            )
            else -> ValidationResult(isValid = true)
        }
    }
    
    /**
     * Valida un nombre (nombre o apellido)
     * @param name Nombre a validar
     * @return Resultado de la validación
     */
    fun validateName(name: String): ValidationResult {
        return when {
            name.isEmpty() -> ValidationResult(
                isValid = false,
                errorMessage = TextResources.ERROR_NAME_REQUIRED
            )
            name.length < Constants.MIN_NAME_LENGTH -> ValidationResult(
                isValid = false,
                errorMessage = TextResources.ERROR_NAME_TOO_SHORT
            )
            name.length > Constants.MAX_NAME_LENGTH -> ValidationResult(
                isValid = false,
                errorMessage = TextResources.ERROR_NAME_TOO_LONG
            )
            !name.all { it.isLetter() || it.isWhitespace() } -> ValidationResult(
                isValid = false,
                errorMessage = TextResources.ERROR_NAME_LETTERS_ONLY
            )
            else -> ValidationResult(isValid = true)
        }
    }
    
    /**
     * Valida un número de teléfono
     * @param phone Teléfono a validar
     * @return Resultado de la validación
     */
    fun validatePhone(phone: String): ValidationResult {
        return when {
            phone.isEmpty() -> ValidationResult(isValid = false, errorMessage = "El teléfono es obligatorio")
            !phone.all { it.isDigit() } -> ValidationResult(isValid = false, errorMessage = "El teléfono solo puede contener números")
            phone.length < 8 -> ValidationResult(
                isValid = false,
                errorMessage = "El teléfono debe tener al menos 8 dígitos"
            )
            phone.length > 15 -> ValidationResult(
                isValid = false,
                errorMessage = "El teléfono no puede tener más de 15 dígitos"
            )
            else -> ValidationResult(isValid = true)
        }
    }

    /**
     * Valida una dirección
     * @param address Dirección a validar
     * @return Resultado de la validación
     */
    fun validateAddress(address: String): ValidationResult {
        return when {
            address.isEmpty() -> ValidationResult(isValid = false, errorMessage = "La dirección es obligatoria")
            address.length < 5 -> ValidationResult(isValid = false, errorMessage = "La dirección debe tener al menos 5 caracteres")
            else -> ValidationResult(isValid = true)
        }
    }
    
    /**
     * Valida un precio
     * @param price Precio a validar
     * @return Resultado de la validación
     */
    fun validatePrice(price: String): ValidationResult {
        return when {
            price.isEmpty() -> ValidationResult(
                isValid = false,
                errorMessage = TextResources.ERROR_INVALID_PRICE
            )
            price.toIntOrNull() == null -> ValidationResult(
                isValid = false,
                errorMessage = TextResources.ERROR_INVALID_PRICE
            )
            price.toInt() <= 0 -> ValidationResult(
                isValid = false,
                errorMessage = TextResources.ERROR_PRICE_ZERO
            )
            price.toInt() > Constants.MAX_PRICE -> ValidationResult(
                isValid = false,
                errorMessage = TextResources.ERROR_MAX_PRICE
            )
            else -> ValidationResult(isValid = true)
        }
    }
    
    /**
     * Valida el stock
     * @param stock Stock a validar
     * @return Resultado de la validación
     */
    fun validateStock(stock: String): ValidationResult {
        return when {
            stock.isEmpty() -> ValidationResult(
                isValid = false,
                errorMessage = TextResources.ERROR_INVALID_STOCK
            )
            stock.toIntOrNull() == null -> ValidationResult(
                isValid = false,
                errorMessage = TextResources.ERROR_INVALID_STOCK
            )
            stock.toInt() < 0 -> ValidationResult(
                isValid = false,
                errorMessage = TextResources.ERROR_NEGATIVE_STOCK
            )
            stock.toInt() > Constants.MAX_STOCK -> ValidationResult(
                isValid = false,
                errorMessage = TextResources.ERROR_MAX_STOCK
            )
            else -> ValidationResult(isValid = true)
        }
    }
}

/**
 * Clase de datos que representa el resultado de una validación
 * @property isValid Indica si la validación fue exitosa
 * @property errorMessage Mensaje de error si la validación falló
 */
data class ValidationResult(
    val isValid: Boolean,
    val errorMessage: String? = null
)
