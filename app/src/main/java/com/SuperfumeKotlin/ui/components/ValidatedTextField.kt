package com.SuperfumeKotlin.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.SuperfumeKotlin.util.ValidationResult

@Composable
fun ValidatedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    validation: (String) -> ValidationResult,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true
) {
    val validationResult = remember(value) { validation(value) }
    
    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            leadingIcon = leadingIcon?.let { icono ->
                { Icon(icono, contentDescription = null) }
            },
            trailingIcon = trailingIcon?.let { icono ->
                { 
                    IconButton(onClick = { onTrailingIconClick?.invoke() }) {
                        Icon(icono, contentDescription = null)
                    }
                }
            },
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation,
            singleLine = singleLine,
            isError = !validationResult.isValid && value.isNotEmpty(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (validationResult.isValid) Color(0xFF6B73FF) else Color(0xFFD32F2F),
                focusedLabelColor = if (validationResult.isValid) Color(0xFF6B73FF) else Color(0xFFD32F2F),
                errorBorderColor = Color(0xFFD32F2F),
                errorLabelColor = Color(0xFFD32F2F)
            ),
            modifier = modifier
        )
        
        // Error message with animation
        AnimatedVisibility(
            visible = !validationResult.isValid && value.isNotEmpty(),
            enter = fadeIn(animationSpec = tween(300))
        ) {
            Row(
                modifier = Modifier.padding(top = 4.dp, start = 16.dp),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Warning,
                    contentDescription = null,
                    tint = Color(0xFFD32F2F),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = validationResult.errorMessage ?: "",
                    color = Color(0xFFD32F2F),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
        
        // Success indicator
        AnimatedVisibility(
            visible = validationResult.isValid && value.isNotEmpty(),
            enter = fadeIn(animationSpec = tween(300))
        ) {
            Row(
                modifier = Modifier.padding(top = 4.dp, start = 16.dp),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Campo válido",
                    color = Color(0xFF4CAF50),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

// Validation functions
fun validateEmail(email: String): ValidationResult {
    return when {
        email.isEmpty() -> ValidationResult(false, "El email es obligatorio")
        !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> 
            ValidationResult(false, "Formato de email inválido")
        email.length < 5 -> 
            ValidationResult(false, "El email debe tener al menos 5 caracteres")
        else -> ValidationResult(true)
    }
}

fun validatePassword(password: String): ValidationResult {
    return when {
        password.isEmpty() -> ValidationResult(false, "La contraseña es obligatoria")
        password.length < 6 -> 
            ValidationResult(false, "La contraseña debe tener al menos 6 caracteres")
        password.length > 20 -> 
            ValidationResult(false, "La contraseña no puede tener más de 20 caracteres")
        !password.any { it.isDigit() } -> 
            ValidationResult(false, "La contraseña debe contener al menos un número")
        !password.any { it.isLetter() } -> 
            ValidationResult(false, "La contraseña debe contener al menos una letra")
        else -> ValidationResult(true)
    }
}

fun validateName(name: String): ValidationResult {
    return when {
        name.isEmpty() -> ValidationResult(false, "El nombre es obligatorio")
        name.length < 2 -> 
            ValidationResult(false, "El nombre debe tener al menos 2 caracteres")
        name.length > 50 -> 
            ValidationResult(false, "El nombre no puede tener más de 50 caracteres")
        !name.all { it.isLetter() || it.isWhitespace() } -> 
            ValidationResult(false, "El nombre solo puede contener letras y espacios")
        else -> ValidationResult(true)
    }
}

fun validatePhone(phone: String): ValidationResult {
    return when {
        phone.isEmpty() -> ValidationResult(true) // Phone is optional
        phone.length < 8 -> 
            ValidationResult(false, "El teléfono debe tener al menos 8 dígitos")
        phone.length > 15 -> 
            ValidationResult(false, "El teléfono no puede tener más de 15 dígitos")
        !phone.all { it.isDigit() || it == '+' || it == '-' || it == ' ' } -> 
            ValidationResult(false, "Formato de teléfono inválido")
        else -> ValidationResult(true)
    }
}

fun validatePrice(price: String): ValidationResult {
    return when {
        price.isEmpty() -> ValidationResult(false, "El precio es obligatorio")
        price.toDoubleOrNull() == null -> 
            ValidationResult(false, "El precio debe ser un número válido")
        price.toDouble() <= 0 -> 
            ValidationResult(false, "El precio debe ser mayor a 0")
        price.toDouble() > 10000 -> 
            ValidationResult(false, "El precio no puede ser mayor a $10,000")
        else -> ValidationResult(true)
    }
}

fun validateStock(stock: String): ValidationResult {
    return when {
        stock.isEmpty() -> ValidationResult(false, "El stock es obligatorio")
        stock.toIntOrNull() == null -> 
            ValidationResult(false, "El stock debe ser un número entero")
        stock.toInt() < 0 -> 
            ValidationResult(false, "El stock no puede ser negativo")
        stock.toInt() > 1000 -> 
            ValidationResult(false, "El stock no puede ser mayor a 1000")
        else -> ValidationResult(true)
    }
}
