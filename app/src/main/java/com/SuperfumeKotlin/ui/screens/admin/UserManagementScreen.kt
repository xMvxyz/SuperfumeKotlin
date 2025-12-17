package com.SuperfumeKotlin.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.SuperfumeKotlin.data.model.dto.request.UpdateUserRequest
import com.SuperfumeKotlin.data.model.dto.response.UsuarioResponse
import com.SuperfumeKotlin.ui.viewmodel.UserViewModel

/**
 * Pantalla de gestión de usuarios (Admin)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserManagementScreen(
    viewModel: UserViewModel = hiltViewModel()
) {
    val usuarios by viewModel.usuarios.collectAsState()
    val estaCargando by viewModel.estaCargando.collectAsState()
    val mensajeError by viewModel.mensajeError.collectAsState()
    val mensajeExito by viewModel.mensajeExito.collectAsState()
    val consultaBusqueda by viewModel.consultaBusqueda.collectAsState()
    val usuarioSeleccionado by viewModel.usuarioSeleccionado.collectAsState()
    
    var showEditDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var usuarioAEliminar by remember { mutableStateOf<UsuarioResponse?>(null) }
    
    LaunchedEffect(Unit) {
        viewModel.cargarUsuarios()
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            .padding(16.dp)
    ) {
        // Barra de búsqueda
        OutlinedTextField(
            value = consultaBusqueda,
            onValueChange = { viewModel.buscarUsuarios(it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Buscar usuarios...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )
        
        Spacer(Modifier.height(16.dp))
        
        // Mensajes
        mensajeError?.let {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE))
            ) {
                Text(
                    text = it,
                    color = Color(0xFFD32F2F),
                    modifier = Modifier.padding(12.dp)
                )
            }
            Spacer(Modifier.height(8.dp))
        }
        
        mensajeExito?.let {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
            ) {
                Text(
                    text = it,
                    color = Color(0xFF4CAF50),
                    modifier = Modifier.padding(12.dp)
                )
            }
            Spacer(Modifier.height(8.dp))
        }
        
        // Lista de usuarios
        if (estaCargando) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            val usuariosFiltrados = viewModel.getUsuariosFiltrados()
            
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(usuariosFiltrados) { usuario ->
                    UserCard(
                        usuario = usuario,
                        onEdit = {
                            viewModel.seleccionarUsuario(usuario)
                            showEditDialog = true
                        },
                        onDelete = {
                            usuarioAEliminar = usuario
                            showDeleteDialog = true
                        },
                        onChangeRole = { nuevoRolId ->
                            viewModel.cambiarRol(usuario.id, nuevoRolId)
                        }
                    )
                }
            }
        }
    }
    
    // Diálogo de edición
    if (showEditDialog && usuarioSeleccionado != null) {
        UserEditDialog(
            usuario = usuarioSeleccionado!!,
            onDismiss = {
                showEditDialog = false
                viewModel.seleccionarUsuario(null)
            },
            onConfirm = { request ->
                viewModel.actualizarUsuario(usuarioSeleccionado!!.id, request)
                showEditDialog = false
            }
        )
    }
    
    // Diálogo de confirmación de eliminación
    if (showDeleteDialog && usuarioAEliminar != null) {
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
                usuarioAEliminar = null
            },
            title = { Text("Confirmar eliminación") },
            text = { Text("¿Estás seguro de eliminar a ${usuarioAEliminar!!.nombre}?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.eliminarUsuario(usuarioAEliminar!!.id)
                        showDeleteDialog = false
                        usuarioAEliminar = null
                    }
                ) {
                    Text("Eliminar", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDeleteDialog = false
                    usuarioAEliminar = null
                }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun UserCard(
    usuario: UsuarioResponse,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onChangeRole: (Int) -> Unit
) {
    var showRoleMenu by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = usuario.nombre,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = usuario.correo,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Spacer(Modifier.height(4.dp))
                    AssistChip(
                        onClick = { showRoleMenu = true },
                        label = { Text(usuario.rol.nombre) },
                        leadingIcon = {
                            Icon(
                                if (usuario.rol.id == 1) Icons.Default.Star else Icons.Default.Person,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                        },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = if (usuario.rol.id == 1) Color(0xFFFFE082) else Color(0xFFE0E0E0)
                        )
                    )
                    
                    // Menú de cambio de rol
                    DropdownMenu(
                        expanded = showRoleMenu,
                        onDismissRequest = { showRoleMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Admin") },
                            onClick = {
                                onChangeRole(1)
                                showRoleMenu = false
                            },
                            leadingIcon = { Icon(Icons.Default.Star, contentDescription = null) }
                        )
                        DropdownMenuItem(
                            text = { Text("Cliente") },
                            onClick = {
                                onChangeRole(2)
                                showRoleMenu = false
                            },
                            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) }
                        )
                    }
                }
                
                Row {
                    IconButton(onClick = onEdit) {
                        Icon(Icons.Default.Edit, contentDescription = "Editar", tint = Color(0xFF6B73FF))
                    }
                    IconButton(onClick = onDelete) {
                        Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red)
                    }
                }
            }
            
            if (usuario.telefono != null || usuario.direccion != null) {
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                usuario.telefono?.let {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Phone, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(8.dp))
                        Text(it, fontSize = 14.sp)
                    }
                }
                usuario.direccion?.let {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(8.dp))
                        Text(it, fontSize = 14.sp)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserEditDialog(
    usuario: UsuarioResponse,
    onDismiss: () -> Unit,
    onConfirm: (UpdateUserRequest) -> Unit
) {
    var nombre by remember { mutableStateOf(usuario.nombre) }
    var correo by remember { mutableStateOf(usuario.correo) }
    var telefono by remember { mutableStateOf(usuario.telefono ?: "") }
    var direccion by remember { mutableStateOf(usuario.direccion ?: "") }
    var rut by remember { mutableStateOf("") }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Editar Usuario") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = correo,
                    onValueChange = { correo = it },
                    label = { Text("Correo") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = telefono,
                    onValueChange = { telefono = it },
                    label = { Text("Teléfono") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = direccion,
                    onValueChange = { direccion = it },
                    label = { Text("Dirección") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(
                        UpdateUserRequest(
                            nombre = nombre,
                            correo = correo,
                            telefono = telefono.ifBlank { null },
                            direccion = direccion.ifBlank { null },
                            rut = rut.ifBlank { null }
                        )
                    )
                }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
