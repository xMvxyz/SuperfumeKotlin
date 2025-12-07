package com.SuperfumeKotlin.ui.screens.profile

import android.Manifest
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material.icons.outlined.PhotoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.SuperfumeKotlin.BuildConfig
import com.SuperfumeKotlin.ui.components.ValidatedTextField
import com.SuperfumeKotlin.ui.viewmodel.ViewModelAutenticacion
import com.SuperfumeKotlin.util.ValidadorFormularios
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onNavigateBack: () -> Unit,
    onNavigateToLogin: () -> Unit,
    authViewModel: ViewModelAutenticacion = hiltViewModel()
) {
    val usuarioActual by authViewModel.usuarioActual.collectAsState()

    var isInEditMode by remember { mutableStateOf(false) }

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    LaunchedEffect(usuarioActual) {
        usuarioActual?.let {
            firstName = it.firstName
            lastName = it.lastName
            phone = it.phone ?: ""
            address = it.address ?: ""
        }
    }

    var showLogoutDialog by remember { mutableStateOf(false) }
    var imageUri by rememberSaveable { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            imageUri = it
            authViewModel.updateProfileImage(it)
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success: Boolean ->
        if (success) {
            imageUri?.let {
                authViewModel.updateProfileImage(it)
            }
        }
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                val uri = createImageUri(context)
                imageUri = uri
                cameraLauncher.launch(uri)
            }
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isInEditMode) "Editar Perfil" else "Mi Perfil",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6B73FF)
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        if (isInEditMode) {
                            isInEditMode = false
                        } else {
                            onNavigateBack()
                        }
                    }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        if (usuarioActual == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier.padding(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = Color(0xFF6B73FF)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "No has iniciado sesión",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = onNavigateToLogin,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF6B73FF)
                            )
                        ) {
                            Text("Iniciar Sesión")
                        }
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .background(Color(0xFFF8F9FA))
            ) {
                // Profile Header
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE0E0E0))
                                .clickable { /* Handle click if necessary */ },
                            contentAlignment = Alignment.Center
                        ) {
                            val profileImage = imageUri ?: usuarioActual?.profileImageUri
                            if (profileImage != null) {
                                AsyncImage(
                                    model = profileImage,
                                    contentDescription = "Foto de perfil",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Icon(
                                    Icons.Default.Person,
                                    contentDescription = null,
                                    modifier = Modifier.size(60.dp),
                                    tint = Color.Gray
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        if (!isInEditMode) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(onClick = { galleryLauncher.launch("image/*") }) {
                                    Icon(Icons.Outlined.PhotoLibrary, contentDescription = "Galería")
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                IconButton(onClick = { cameraPermissionLauncher.launch(Manifest.permission.CAMERA) }) {
                                    Icon(Icons.Outlined.PhotoCamera, contentDescription = "Cámara")
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "${usuarioActual?.firstName ?: ""} ${usuarioActual?.lastName ?: ""}",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2C3E50),
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = usuarioActual?.email ?: "",
                            fontSize = 16.sp,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                // Profile Information
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "Información Personal",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2C3E50),
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        if (isInEditMode) {
                            ValidatedTextField(value = firstName, onValueChange = { firstName = it }, label = "Nombre", validation = ValidadorFormularios::validarNombre)
                            ValidatedTextField(value = lastName, onValueChange = { lastName = it }, label = "Apellido", validation = ValidadorFormularios::validarNombre)
                            ValidatedTextField(value = phone, onValueChange = { phone = it }, label = "Teléfono", validation = ValidadorFormularios::validarTelefono, leadingIcon = Icons.Default.Phone)
                            ValidatedTextField(value = address, onValueChange = { address = it }, label = "Dirección", validation = ValidadorFormularios::validarDireccion, leadingIcon = Icons.Default.LocationOn)
                        } else {
                            ProfileInfoItem(icon = Icons.Default.Person, label = "Nombre", value = usuarioActual?.firstName ?: "")
                            ProfileInfoItem(icon = Icons.Default.Person, label = "Apellido", value = usuarioActual?.lastName ?: "")
                            ProfileInfoItem(icon = Icons.Default.Email, label = "Email", value = usuarioActual?.email ?: "")
                            if ((usuarioActual?.phone ?: "").isNotBlank()) {
                                ProfileInfoItem(icon = Icons.Default.Phone, label = "Teléfono", value = usuarioActual?.phone ?: "No especificado")
                            }
                            if ((usuarioActual?.address ?: "").isNotBlank()) {
                                ProfileInfoItem(icon = Icons.Default.LocationOn, label = "Dirección", value = usuarioActual?.address ?: "No especificada")
                            }
                        }
                    }
                }

                // Actions
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        if (isInEditMode) {
                            Button(
                                onClick = {
                                    authViewModel.updateUser(firstName, lastName, phone, address)
                                    isInEditMode = false
                                },
                                modifier = Modifier.fillMaxWidth(),
                                enabled = ValidadorFormularios.validarNombre(firstName).esValido &&
                                        ValidadorFormularios.validarNombre(lastName).esValido &&
                                        ValidadorFormularios.validarTelefono(phone).esValido &&
                                        ValidadorFormularios.validarDireccion(address).esValido
                            ) {
                                Icon(Icons.Default.Save, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Guardar Cambios")
                            }
                            OutlinedButton(
                                onClick = { isInEditMode = false },
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Text("Cancelar")
                            }
                        } else {
                            OutlinedButton(
                                onClick = { isInEditMode = true },
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Icon(Icons.Default.Edit, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Editar Perfil")
                            }
                            Button(
                                onClick = { showLogoutDialog = true },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
                            ) {
                                Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Cerrar Sesión")
                            }
                        }
                    }
                }
            }
        }

        if (showLogoutDialog) {
            AlertDialog(
                onDismissRequest = { showLogoutDialog = false },
                title = {
                    Text("Cerrar Sesión")
                },
                text = {
                    Text("¿Estás seguro de que quieres cerrar sesión?")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            authViewModel.cerrarSesion()
                            showLogoutDialog = false
                            onNavigateToLogin()
                        }
                    ) {
                        Text("Sí, cerrar sesión")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showLogoutDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}

@Composable
fun ProfileInfoItem(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = Color(0xFF6B73FF),
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = label,
                fontSize = 12.sp,
                color = Color.Gray
            )
            Text(
                text = value,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF2C3E50)
            )
        }
    }
}

private fun createImageUri(context: Context): Uri {
    val imageFile = File.createTempFile(
        "JPEG_${System.currentTimeMillis()}_",
        ".jpg",
        context.externalCacheDir
    )
    return FileProvider.getUriForFile(
        context,
        "${BuildConfig.APPLICATION_ID}.provider",
        imageFile
    )
}
