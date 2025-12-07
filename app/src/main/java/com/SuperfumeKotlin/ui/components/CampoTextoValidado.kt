package com.SuperfumeKotlin.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.focusable
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
import com.SuperfumeKotlin.util.RecursosTexto
import com.SuperfumeKotlin.util.ValidadorFormularios
import com.SuperfumeKotlin.util.ResultadoValidacion

/**
 * Campo de texto con validación integrada
 * Muestra errores y estados de éxito en tiempo real
 */
@Composable
fun CampoTextoValidado(
    valor: String,
    onValorCambiado: (String) -> Unit,
    etiqueta: String,
    iconoInicial: ImageVector? = null,
    iconoFinal: ImageVector? = null,
    onIconoFinalClick: (() -> Unit)? = null,
    opcionesTeclado: KeyboardOptions = KeyboardOptions.Default,
    transformacionVisual: VisualTransformation = VisualTransformation.None,
    validacion: (String) -> ResultadoValidacion,
    modificador: Modifier = Modifier,
    unaLinea: Boolean = true
) {
    val resultadoValidacion = remember(valor) { validacion(valor) }
    
    Column(modifier = modificador) {
        OutlinedTextField(
            value = valor,
            onValueChange = onValorCambiado,
            label = { Text(etiqueta) },
            leadingIcon = iconoInicial?.let { icono ->
                { Icon(icono, contentDescription = null) }
            },
            trailingIcon = iconoFinal?.let { icono ->
                { 
                    IconButton(onClick = { onIconoFinalClick?.invoke() }) {
                        Icon(icono, contentDescription = null)
                    }
                }
            },
            keyboardOptions = opcionesTeclado,
            visualTransformation = transformacionVisual,
            singleLine = unaLinea,
            isError = !resultadoValidacion.esValido && valor.isNotEmpty(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (resultadoValidacion.esValido) Color(0xFF6B73FF) else Color(0xFFD32F2F),
                focusedLabelColor = if (resultadoValidacion.esValido) Color(0xFF6B73FF) else Color(0xFFD32F2F),
                errorBorderColor = Color(0xFFD32F2F),
                errorLabelColor = Color(0xFFD32F2F)
            ),
            modifier = modificador
        )
        
        // Mensaje de error con animación
        AnimatedVisibility(
            visible = !resultadoValidacion.esValido && valor.isNotEmpty(),
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
                    text = resultadoValidacion.mensajeError ?: "",
                    color = Color(0xFFD32F2F),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
        
        // Indicador de éxito
        AnimatedVisibility(
            visible = resultadoValidacion.esValido && valor.isNotEmpty(),
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
                    text = RecursosTexto.EXITO_CAMPO_VALIDO,
                    color = Color(0xFF4CAF50),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

// Funciones de validación específicas usando el validador centralizado
fun validarEmail(email: String): ResultadoValidacion = ValidadorFormularios.validarEmail(email)
fun validarContraseña(contraseña: String): ResultadoValidacion = ValidadorFormularios.validarContraseña(contraseña)
fun validarNombre(nombre: String): ResultadoValidacion = ValidadorFormularios.validarNombre(nombre)
fun validarTelefono(telefono: String): ResultadoValidacion = ValidadorFormularios.validarTelefono(telefono)
fun validarPrecio(precio: String): ResultadoValidacion = ValidadorFormularios.validarPrecio(precio)
fun validarStock(stock: String): ResultadoValidacion = ValidadorFormularios.validarStock(stock)
