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
import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.SuperfumeKotlin.data.model.Usuario
import com.SuperfumeKotlin.ui.viewmodel.NavegacionEvento
import com.SuperfumeKotlin.ui.viewmodel.ViewModelAutenticacion
import com.SuperfumeKotlin.util.ValidationResult
import com.SuperfumeKotlin.util.FormValidators
import com.SuperfumeKotlin.util.ImagePickerHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: ViewModelAutenticacion = hiltViewModel()
) {
    val estaCargando by viewModel.estaCargando.collectAsState()
    val mensajeError by viewModel.mensajeError.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var rut by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    var showImageSourceDialog by remember { mutableStateOf(false) }
    
    val context = LocalContext.current
    
    // Launcher para galería
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { profileImageUri = it }
    }
    
    // Launcher para cámara
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (!success) {
            profileImageUri = null
        }
    }

    // Validations
    val nombreValidation = remember(firstName) { FormValidators.validateName(firstName) }
    val apellidoValidation = remember(lastName) { FormValidators.validateName(lastName) }
    val emailValidation = remember(email) { FormValidators.validateEmail(email) }
    val telefonoValidation = remember(phone) { FormValidators.validatePhone(phone) }
    val direccionValidation = remember(address) { FormValidators.validateAddress(address) }
    val passwordValidation = remember(password) { FormValidators.validatePassword(password) }

    // Observar eventos de navegación de un solo uso
    LaunchedEffect(Unit) {
        viewModel.eventoNavegacion.collect { evento ->
            when (evento) {
                is NavegacionEvento.NavegarAHome -> onNavigateToHome()
            }
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
                    
                    // Foto de perfil
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .background(Color.LightGray)
                                .clickable { showImageSourceDialog = true },
                            contentAlignment = Alignment.Center
                        ) {
                            if (profileImageUri != null) {
                                Image(
                                    painter = rememberAsyncImagePainter(profileImageUri),
                                    contentDescription = "Foto de perfil",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Agregar foto",
                                    modifier = Modifier.size(50.dp),
                                    tint = Color.Gray
                                )
                            }
                        }
                        Text(
                            "Toca para agregar foto",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    // Nombre
                    OutlinedTextField(value = firstName, onValueChange = { firstName = it }, label = { Text("Nombre") }, isError = !nombreValidation.isValid && firstName.isNotEmpty(), supportingText = { if (!nombreValidation.isValid && firstName.isNotEmpty()) Text(nombreValidation.errorMessage ?: "") })
                    
                    // Apellido
                    OutlinedTextField(value = lastName, onValueChange = { lastName = it }, label = { Text("Apellido") }, isError = !apellidoValidation.isValid && lastName.isNotEmpty(), supportingText = { if (!apellidoValidation.isValid && lastName.isNotEmpty()) Text(apellidoValidation.errorMessage ?: "") })

                    // Email
                    OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email), isError = !emailValidation.isValid && email.isNotEmpty(), supportingText = { if (!emailValidation.isValid && email.isNotEmpty()) Text(emailValidation.errorMessage ?: "") })

                    // Telefono
                    OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Teléfono") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), isError = !telefonoValidation.isValid && phone.isNotEmpty(), supportingText = { if (!telefonoValidation.isValid && phone.isNotEmpty()) Text(telefonoValidation.errorMessage ?: "") })
                    
                    // Direccion
                    OutlinedTextField(value = address, onValueChange = { address = it }, label = { Text("Dirección") }, isError = !direccionValidation.isValid && address.isNotEmpty(), supportingText = { if (!direccionValidation.isValid && address.isNotEmpty()) Text(direccionValidation.errorMessage ?: "") })
                    
                    // RUT
                    OutlinedTextField(
                        value = rut,
                        onValueChange = { rut = it },
                        label = { Text("RUT (opcional)") },
                        modifier = Modifier.fillMaxWidth()
                    )

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
                        isError = !passwordValidation.isValid && password.isNotEmpty(),
                        supportingText = { if (!passwordValidation.isValid && password.isNotEmpty()) Text(passwordValidation.errorMessage ?: "") }
                    )

                    AnimatedVisibility(visible = mensajeError != null) {
                        Text(mensajeError ?: "", color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 8.dp))
                    }

                    Button(
                        onClick = {
                            val usuario = Usuario(email = email, password = password, firstName = firstName, lastName = lastName, phone = phone, address = address)
                            viewModel.registrarUsuario(usuario, rut = if (rut.isNotBlank()) rut else null)
                            profileImageUri?.let { viewModel.updateProfileImage(it) }
                        },
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp).height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6B73FF)),
                        enabled = !estaCargando && 
                                nombreValidation.isValid && 
                                apellidoValidation.isValid && 
                                emailValidation.isValid && 
                                telefonoValidation.isValid && 
                                direccionValidation.isValid && 
                                passwordValidation.isValid
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
            
            // Diálogo para seleccionar fuente de imagen
            if (showImageSourceDialog) {
                AlertDialog(
                    onDismissRequest = { showImageSourceDialog = false },
                    title = { Text("Seleccionar foto") },
                    text = {
                        Column {
                            TextButton(
                                onClick = {
                                    showImageSourceDialog = false
                                    galleryLauncher.launch("image/*")
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Icon(Icons.Default.Photo, "Galería")
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Text("Seleccionar de galería")
                                }
                            }
                            TextButton(
                                onClick = {
                                    showImageSourceDialog = false
                                    val uri = ImagePickerHelper.createImageUri(context)
                                    profileImageUri = uri
                                    cameraLauncher.launch(uri)
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Icon(Icons.Default.CameraAlt, "Cámara")
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Text("Tomar foto")
                                }
                            }
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = { showImageSourceDialog = false }) {
                            Text("Cancelar")
                        }
                    }
                )
            }
        }
    }
}
