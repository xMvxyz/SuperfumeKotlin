package com.SuperfumeKotlin.ui.screens.admin

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.collectAsState
import com.SuperfumeKotlin.data.model.Perfume
import com.SuperfumeKotlin.ui.components.ImagePicker
import com.SuperfumeKotlin.ui.components.ValidatedTextField
import com.SuperfumeKotlin.ui.components.validateName
import com.SuperfumeKotlin.ui.components.validatePrice
import com.SuperfumeKotlin.ui.components.validateStock
import com.SuperfumeKotlin.ui.viewmodel.ViewModelPerfume

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPerfumeScreen(
    onNavigateBack: () -> Unit,
    viewModel: ViewModelPerfume = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var brand by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Frescos") }
    var size by remember { mutableStateOf("100ml") }
    var gender by remember { mutableStateOf("Unisex") }
    var stock by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    
    val estaCargando by viewModel.estaCargando.collectAsState()
    val mensajeError by viewModel.mensajeError.collectAsState()
    
    var showSuccessDialog by remember { mutableStateOf(false) }
    
    val categories = listOf("Frescos", "Florales", "Orientales", "Amaderados", "Cítricos")
    val sizes = listOf("30ml", "50ml", "75ml", "100ml", "150ml")
    val genders = listOf("Masculino", "Femenino", "Unisex")
    
    LaunchedEffect(showSuccessDialog) {
        if (showSuccessDialog) {
            kotlinx.coroutines.delay(2000)
            showSuccessDialog = false
            onNavigateBack()
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Agregar Perfume",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6B73FF)
                ),
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF8F9FA))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                // Form Card
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn() + slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(800)
                    )
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = "Información del Perfume",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2C3E50)
                            )
                            
                            // Image Picker
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                ImagePicker(
                                    imageUri = imageUri,
                                    onImageSelected = { imageUri = it },
                                    contentDescription = "Imagen del perfume"
                                )
                            }
                            
                            // Name Field
                            ValidatedTextField(
                                value = name,
                                onValueChange = { name = it },
                                label = "Nombre del Perfume",
                                leadingIcon = Icons.Default.Favorite,
                                validation = ::validateName
                            )
                            
                            // Brand Field
                            ValidatedTextField(
                                value = brand,
                                onValueChange = { brand = it },
                                label = "Marca",
                                leadingIcon = Icons.Default.Star,
                                validation = ::validateName
                            )
                            
                            // Price Field
                            ValidatedTextField(
                                value = price,
                                onValueChange = { price = it },
                                label = "Precio ($)",
                                leadingIcon = Icons.Default.Add,
                                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                                    keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal
                                ),
                                validation = ::validatePrice
                            )
                            
                            // Stock Field
                            ValidatedTextField(
                                value = stock,
                                onValueChange = { stock = it },
                                label = "Stock",
                                leadingIcon = Icons.Default.Info,
                                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                                    keyboardType = androidx.compose.ui.text.input.KeyboardType.Number
                                ),
                                validation = ::validateStock
                            )
                            
                            // Category Dropdown
                            ExposedDropdownMenuBox(
                                expanded = false,
                                onExpandedChange = { }
                            ) {
                                OutlinedTextField(
                                    value = category,
                                    onValueChange = { },
                                    readOnly = true,
                                    label = { Text("Categoría") },
                                    leadingIcon = { Icon(Icons.Default.Star, contentDescription = null) },
                                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                ExposedDropdownMenu(
                                    expanded = false,
                                    onDismissRequest = { }
                                ) {
                                    categories.forEach { cat ->
                                        DropdownMenuItem(
                                            text = { Text(cat) },
                                            onClick = { category = cat }
                                        )
                                    }
                                }
                            }
                            
                            // Size Dropdown
                            ExposedDropdownMenuBox(
                                expanded = false,
                                onExpandedChange = { }
                            ) {
                                OutlinedTextField(
                                    value = size,
                                    onValueChange = { },
                                    readOnly = true,
                                    label = { Text("Tamaño") },
                                    leadingIcon = { Icon(Icons.Default.Settings, contentDescription = null) },
                                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                ExposedDropdownMenu(
                                    expanded = false,
                                    onDismissRequest = { }
                                ) {
                                    sizes.forEach { s ->
                                        DropdownMenuItem(
                                            text = { Text(s) },
                                            onClick = { size = s }
                                        )
                                    }
                                }
                            }
                            
                            // Gender Dropdown
                            ExposedDropdownMenuBox(
                                expanded = false,
                                onExpandedChange = { }
                            ) {
                                OutlinedTextField(
                                    value = gender,
                                    onValueChange = { },
                                    readOnly = true,
                                    label = { Text("Género") },
                                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                ExposedDropdownMenu(
                                    expanded = false,
                                    onDismissRequest = { }
                                ) {
                                    genders.forEach { g ->
                                        DropdownMenuItem(
                                            text = { Text(g) },
                                            onClick = { gender = g }
                                        )
                                    }
                                }
                            }
                            
                            // Description Field
                            OutlinedTextField(
                                value = description,
                                onValueChange = { description = it },
                                label = { Text("Descripción") },
                                leadingIcon = { Icon(Icons.Default.Edit, contentDescription = null) },
                                modifier = Modifier.fillMaxWidth(),
                                minLines = 3,
                                maxLines = 5
                            )
                            
                            // Error Message
                            AnimatedVisibility(visible = mensajeError != null) {
                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color(0xFFFFEBEE)
                                    ),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = mensajeError ?: "",
                                        color = Color(0xFFD32F2F),
                                        modifier = Modifier.padding(12.dp)
                                    )
                                }
                            }
                            
                            // Submit Button
                            Button(
                                onClick = {
                                    val newPerfume = Perfume(
                                        name = name,
                                        brand = brand,
                                        price = price.toDoubleOrNull() ?: 0.0,
                                        description = description,
                                        category = category,
                                        size = size,
                                        gender = gender,
                                        stock = stock.toIntOrNull() ?: 0,
                                        imageUri = imageUri?.toString()
                                    )
                            viewModel.agregarPerfume(newPerfume)
                                    showSuccessDialog = true
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF6B73FF)
                                ),
                                enabled = !estaCargando && name.isNotBlank() && brand.isNotBlank() && 
                                        price.isNotBlank() && stock.isNotBlank() && 
                                        description.isNotBlank() && !estaCargando
                            ) {
                                if (estaCargando) {
                                    CircularProgressIndicator(
                                        color = Color.White,
                                        modifier = Modifier.size(20.dp)
                                    )
                                } else {
                                    Icon(
                                        Icons.Default.Add,
                                        contentDescription = null,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Agregar Perfume",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        
        // Success Dialog
        AnimatedVisibility(
            visible = showSuccessDialog,
            enter = fadeIn() + slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(300)
            )
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF4CAF50)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "¡Perfume agregado exitosamente!",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
