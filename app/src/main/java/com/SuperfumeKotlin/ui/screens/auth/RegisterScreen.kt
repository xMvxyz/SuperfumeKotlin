package com.SuperfumeKotlin.ui.screens.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.SuperfumeKotlin.data.model.Usuario
import com.SuperfumeKotlin.ui.viewmodel.ViewModelAutenticacion
import com.SuperfumeKotlin.util.ResultadoValidacion
import com.SuperfumeKotlin.util.ValidadorFormularios

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: ViewModelAutenticacion = hiltViewModel()
) {
    val estaCargando by viewModel.estaCargando.collectAsState()
    val mensajeError by viewModel.mensajeError.collectAsState()
    val estaLogueado by viewModel.estaLogueado.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    // Validations
    val nombreValidation = remember(firstName) { ValidadorFormularios.validarNombre(firstName) }
    val apellidoValidation = remember(lastName) { ValidadorFormularios.validarNombre(lastName) }
    val emailValidation = remember(email) { ValidadorFormularios.validarEmail(email) }
    val telefonoValidation = remember(phone) { ValidadorFormularios.validarTelefono(phone) }
    val direccionValidation = remember(address) { ValidadorFormularios.validarDireccion(address) }
    val passwordValidation = remember(password) { ValidadorFormularios.validarContraseña(password) }

    LaunchedEffect(estaLogueado) {
        if (estaLogueado) {
            onNavigateToHome()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF757575), Color(0xFF424242))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AnimatedVisibility(
                visible = true,
                enter = fadeIn() + slideInVertically(initialOffsetY = { -it }, animationSpec = tween(1000))
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(bottom = 32.dp)
                ) {
                    Text("Superfume", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.White, textAlign = TextAlign.Center)
                    Text("Únete a nuestra comunidad", fontSize = 16.sp, color = Color.White.copy(alpha = 0.8f), textAlign = TextAlign.Center)
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Crear Cuenta", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2C3E50), modifier = Modifier.padding(bottom = 8.dp))

                    // Nombre
                    OutlinedTextField(value = firstName, onValueChange = { firstName = it }, label = { Text("Nombre") }, isError = !nombreValidation.esValido && firstName.isNotEmpty(), supportingText = { if (!nombreValidation.esValido && firstName.isNotEmpty()) Text(nombreValidation.mensajeError ?: "") })
                    
                    // Apellido
                    OutlinedTextField(value = lastName, onValueChange = { lastName = it }, label = { Text("Apellido") }, isError = !apellidoValidation.esValido && lastName.isNotEmpty(), supportingText = { if (!apellidoValidation.esValido && lastName.isNotEmpty()) Text(apellidoValidation.mensajeError ?: "") })

                    // Email
                    OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email), isError = !emailValidation.esValido && email.isNotEmpty(), supportingText = { if (!emailValidation.esValido && email.isNotEmpty()) Text(emailValidation.mensajeError ?: "") })

                    // Telefono
                    OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Teléfono") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), isError = !telefonoValidation.esValido && phone.isNotEmpty(), supportingText = { if (!telefonoValidation.esValido && phone.isNotEmpty()) Text(telefonoValidation.mensajeError ?: "") })
                    
                    // Direccion
                    OutlinedTextField(value = address, onValueChange = { address = it }, label = { Text("Dirección") }, isError = !direccionValidation.esValido && address.isNotEmpty(), supportingText = { if (!direccionValidation.esValido && address.isNotEmpty()) Text(direccionValidation.mensajeError ?: "") })

                    // Contraseña
                    OutlinedTextField(
                        value = password, 
                        onValueChange = { password = it }, 
                        label = { Text("Contraseña") }, 
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = { 
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility, "Toggle password visibility")
                            }
                        },
                        isError = !passwordValidation.esValido && password.isNotEmpty(),
                        supportingText = { if (!passwordValidation.esValido && password.isNotEmpty()) Text(passwordValidation.mensajeError ?: "") }
                    )

                    AnimatedVisibility(visible = mensajeError != null) {
                        Text(mensajeError ?: "", color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 8.dp))
                    }

                    Button(
                        onClick = {
                            val usuario = Usuario(email = email, password = password, firstName = firstName, lastName = lastName, phone = phone, address = address)
                            viewModel.registrarUsuario(usuario)
                        },
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp).height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6B73FF)),
                        enabled = !estaCargando && 
                                nombreValidation.esValido && 
                                apellidoValidation.esValido && 
                                emailValidation.esValido && 
                                telefonoValidation.esValido && 
                                direccionValidation.esValido && 
                                passwordValidation.esValido
                    ) {
                        if (estaCargando) {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                        } else {
                            Text("Crear Cuenta", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("¿Ya tienes cuenta? ", color = Color.Gray)
                        TextButton(onClick = onNavigateToLogin) {
                            Text("Inicia Sesión", color = Color(0xFF6B73FF), fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}