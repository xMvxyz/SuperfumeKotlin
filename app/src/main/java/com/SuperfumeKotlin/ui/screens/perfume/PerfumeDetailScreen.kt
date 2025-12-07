package com.SuperfumeKotlin.ui.screens.perfume

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.collectAsState
import coil.compose.AsyncImage
import com.SuperfumeKotlin.data.model.Perfume
import com.SuperfumeKotlin.ui.viewmodel.ViewModelPerfume
import com.SuperfumeKotlin.ui.viewmodel.ViewModelCarrito
import com.SuperfumeKotlin.ui.viewmodel.ViewModelAutenticacion

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfumeDetailScreen(
    perfumeId: Long,
    onNavigateBack: () -> Unit,
    onNavigateToCart: () -> Unit,
    perfumeViewModel: ViewModelPerfume = hiltViewModel(),
    cartViewModel: ViewModelCarrito = hiltViewModel(),
    authViewModel: ViewModelAutenticacion = hiltViewModel()
) {
    val usuarioActual by authViewModel.usuarioActual.collectAsState()
    val perfumes by perfumeViewModel.perfumes.collectAsState()
    val estaCargando by perfumeViewModel.estaCargando.collectAsState()
    
    var quantity by remember { mutableStateOf(1) }
    var showAddedToCart by remember { mutableStateOf(false) }
    
    val perfume = perfumes.find { it.id == perfumeId }
    
    LaunchedEffect(showAddedToCart) {
        if (showAddedToCart) {
            kotlinx.coroutines.delay(2000)
            showAddedToCart = false
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Detalles",
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
                },
                actions = {
                    IconButton(onClick = onNavigateToCart) {
                        Icon(
                            Icons.Default.ShoppingCart,
                            contentDescription = "Carrito",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        if (estaCargando) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color(0xFF6B73FF)
                )
            }
        } else if (perfume != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .background(Color(0xFFF8F9FA))
            ) {
                // Perfume Image
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn() + slideInVertically(
                        initialOffsetY = { -it },
                        animationSpec = tween(800)
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .background(Color.White)
                    ) {
                        if (perfume.imageUri != null) {
                            AsyncImage(
                                model = perfume.imageUri,
                                contentDescription = perfume.name,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Favorite,
                                    contentDescription = null,
                                    modifier = Modifier.size(120.dp),
                                    tint = Color(0xFF6B73FF)
                                )
                            }
                        }
                    }
                }
                
                // Perfume Info
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn() + slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(800, delayMillis = 200)
                    )
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Text(
                                text = perfume.name,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2C3E50)
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(
                                text = perfume.brand,
                                fontSize = 18.sp,
                                color = Color(0xFF6B73FF),
                                fontWeight = FontWeight.Medium
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            Text(
                                text = "$${String.format("%.2f", perfume.price)}",
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2C3E50)
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            // Perfume Details
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                DetailItem(
                                    icon = Icons.Default.Star,
                                    label = "Categoría",
                                    value = perfume.category
                                )
                                DetailItem(
                                    icon = Icons.Default.Person,
                                    label = "Género",
                                    value = perfume.gender
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                DetailItem(
                                    icon = Icons.Default.Settings,
                                    label = "Tamaño",
                                    value = perfume.size
                                )
                                DetailItem(
                                    icon = Icons.Default.Info,
                                    label = "Stock",
                                    value = if (perfume.stock > 0) "${perfume.stock} disponibles" else "Agotado"
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(20.dp))
                            
                            // Description
                            Text(
                                text = "Descripción",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2C3E50)
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(
                                text = perfume.description,
                                fontSize = 14.sp,
                                color = Color(0xFF666666),
                                lineHeight = 20.sp
                            )
                            
                            Spacer(modifier = Modifier.height(24.dp))
                            
                            // Quantity Selector
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Cantidad:",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    IconButton(
                                        onClick = { if (quantity > 1) quantity-- }
                                    ) {
                                        Icon(
                                            Icons.Default.Delete,
                                            contentDescription = "Disminuir"
                                        )
                                    }
                                    
                                    Text(
                                        text = quantity.toString(),
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 16.dp)
                                    )
                                    
                                    IconButton(
                                        onClick = { if (quantity < perfume.stock) quantity++ }
                                    ) {
                                        Icon(
                                            Icons.Default.Add,
                                            contentDescription = "Aumentar"
                                        )
                                    }
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(24.dp))
                            
                            // Add to Cart Button
                            Button(
                                onClick = {
                                    usuarioActual?.let { usuario ->
                                        if (perfume.stock > 0) {
                                            cartViewModel.agregarAlCarrito(usuario.id, perfume.id, quantity)
                                            showAddedToCart = true
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF6B73FF)
                                ),
                                enabled = perfume.stock > 0 && usuarioActual != null
                            ) {
                                Icon(
                                    Icons.Default.ShoppingCart,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (perfume.stock > 0) "Agregar al Carrito" else "Agotado",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            
                            if (perfume.stock == 0) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Este perfume está agotado",
                                    color = Color(0xFFD32F2F),
                                    fontSize = 14.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                }
            }
        }
        
        // Success Message
        AnimatedVisibility(
            visible = showAddedToCart,
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
                        text = "¡Agregado al carrito!",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun DetailItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = Color(0xFF6B73FF),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Gray
        )
        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF2C3E50)
        )
    }
}
