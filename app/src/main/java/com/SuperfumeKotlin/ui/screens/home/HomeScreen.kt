package com.SuperfumeKotlin.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.collectAsState
import coil.compose.AsyncImage
import com.SuperfumeKotlin.data.model.Perfume
import com.SuperfumeKotlin.ui.viewmodel.ViewModelPerfume

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToPerfumeDetail: (Long) -> Unit,
    onNavigateToCart: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToLogin: () -> Unit = {},
    onNavigateToAddPerfume: () -> Unit,
    viewModel: ViewModelPerfume = hiltViewModel(),
    authViewModel: com.SuperfumeKotlin.ui.viewmodel.ViewModelAutenticacion = hiltViewModel()
) {
    val perfumes by viewModel.perfumes.collectAsState()
    val estaCargando by viewModel.estaCargando.collectAsState()
    val consultaBusqueda by viewModel.consultaBusqueda.collectAsState()
    val categoriaSeleccionada by viewModel.categoriaSeleccionada.collectAsState()
    val generoSeleccionado by viewModel.generoSeleccionado.collectAsState()
    val estaLogueado by authViewModel.estaLogueado.collectAsState()
    
    var searchText by remember { mutableStateOf("") }
    var showSearchBar by remember { mutableStateOf(false) }
    
    val categories = listOf("Todos", "Frescos", "Florales", "Orientales", "Amaderados", "Cítricos")
    val genders = listOf("Todos", "Masculino", "Femenino", "Unisex")
    
    LaunchedEffect(searchText) {
        viewModel.buscarPerfumes(searchText)
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Superfume",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6B73FF)
                ),
                actions = {
                    IconButton(onClick = { showSearchBar = !showSearchBar }) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Buscar",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = onNavigateToCart) {
                        Icon(
                            Icons.Default.ShoppingCart,
                            contentDescription = "Carrito",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = {
                        if (estaLogueado) {
                            onNavigateToProfile()
                        } else {
                            onNavigateToLogin()
                        }
                    }) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Perfil",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = onNavigateToAddPerfume) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Agregar Perfume",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF8F9FA))
        ) {
            // Search Bar
            AnimatedVisibility(
                visible = showSearchBar,
                enter = fadeIn() + slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(300)
                )
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    OutlinedTextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        label = { Text("Buscar perfumes...") },
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = null)
                        },
                        trailingIcon = {
                            if (searchText.isNotEmpty()) {
                                IconButton(onClick = { searchText = "" }) {
                                    Icon(Icons.Default.Clear, contentDescription = "Limpiar")
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        singleLine = true
                    )
                }
            }
            
            // Categories Filter
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Categorías",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(categories) { category ->
                            FilterChip(
                                onClick = { viewModel.filtrarPorCategoria(category) },
                                label = { Text(category) },
                                selected = categoriaSeleccionada == category,
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = Color(0xFF6B73FF),
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }
                }
            }
            
            // Gender Filter
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Género",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(genders) { gender ->
                            FilterChip(
                                onClick = { viewModel.filtrarPorGenero(gender) },
                                label = { Text(gender) },
                                selected = generoSeleccionado == gender,
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = Color(0xFF6B73FF),
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }
                }
            }
            
            // Perfumes Grid
            if (estaCargando) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Color(0xFF6B73FF)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(perfumes.chunked(2)) { rowPerfumes ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            for (perfume in rowPerfumes) {
                                PerfumeCard(
                                    perfume = perfume,
                                    onClick = { onNavigateToPerfumeDetail(perfume.id) },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            // Fill empty space if odd number of items
                            if (rowPerfumes.size == 1) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PerfumeCard(
    perfume: Perfume,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            // Perfume Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFF0F0F0)),
                contentAlignment = Alignment.Center
            ) {
                if (perfume.imageUri != null) {
                    AsyncImage(
                        model = perfume.imageUri,
                        contentDescription = perfume.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = Color(0xFF6B73FF)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Perfume Info
            Text(
                text = perfume.name,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            
            Text(
                text = perfume.brand,
                fontSize = 12.sp,
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            
            Text(
                text = perfume.category,
                fontSize = 10.sp,
                color = Color(0xFF6B73FF),
                fontWeight = FontWeight.Medium
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = "$${String.format("%.2f", perfume.price)}",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color(0xFF2C3E50)
            )
        }
    }
}
