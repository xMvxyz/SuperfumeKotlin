package com.SuperfumeKotlin.ui.screens.admin

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Pantalla principal de administración con tabs
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(
    onNavigateBack: () -> Unit,
    onNavigateToAddPerfume: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Perfumes", "Usuarios", "Pedidos")
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Panel de Administración",
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
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
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
        ) {
            // Tabs
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.White,
                contentColor = Color(0xFF6B73FF)
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) },
                        icon = {
                            Icon(
                                when (index) {
                                    0 -> Icons.Default.ShoppingCart
                                    1 -> Icons.Default.Person
                                    else -> Icons.Default.List
                                },
                                contentDescription = null
                            )
                        }
                    )
                }
            }
            
            // Content
            when (selectedTab) {
                0 -> PerfumeManagementTab(onNavigateToAddPerfume)
                1 -> UserManagementScreen()
                2 -> OrderManagementTab()
            }
        }
    }
}

@Composable
fun PerfumeManagementTab(onNavigateToAddPerfume: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(
            onClick = onNavigateToAddPerfume,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Agregar Perfume")
        }
        Spacer(Modifier.height(16.dp))
        Text("Gestión de perfumes - En desarrollo")
    }
}

@Composable
fun OrderManagementTab() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Gestión de pedidos - En desarrollo")
    }
}
