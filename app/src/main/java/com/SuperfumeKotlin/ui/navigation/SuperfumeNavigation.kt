package com.SuperfumeKotlin.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.SuperfumeKotlin.ui.screens.admin.AddPerfumeScreen
import com.SuperfumeKotlin.ui.screens.auth.LoginScreen
import com.SuperfumeKotlin.ui.screens.auth.RegisterScreen
import com.SuperfumeKotlin.ui.screens.cart.CartScreen
import com.SuperfumeKotlin.ui.screens.home.HomeScreen
import com.SuperfumeKotlin.ui.screens.perfume.PerfumeDetailScreen
import com.SuperfumeKotlin.ui.screens.profile.ProfileScreen
import com.SuperfumeKotlin.ui.viewmodel.ViewModelAutenticacion
import com.SuperfumeKotlin.util.Constantes

/**
 * Navegación principal de la aplicación Superfume
 * Define todas las rutas y transiciones entre pantallas
 */
@Composable
fun NavegacionSuperfume(navController: NavHostController) {
    val authViewModel: ViewModelAutenticacion = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = Constantes.RUTA_LOGIN
    ) {
        composable(Constantes.RUTA_LOGIN) {
            LoginScreen(
                viewModel = authViewModel,
                onNavigateToRegister = {
                    navController.navigate(Constantes.RUTA_REGISTRO)
                },
                onNavigateToHome = {
                    navController.navigate(Constantes.RUTA_HOME) {
                        popUpTo(Constantes.RUTA_LOGIN) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Constantes.RUTA_REGISTRO) {
            RegisterScreen(
                viewModel = authViewModel,
                onNavigateToLogin = {
                    navController.navigate(Constantes.RUTA_LOGIN)
                },
                onNavigateToHome = {
                    navController.navigate(Constantes.RUTA_HOME) {
                        popUpTo(Constantes.RUTA_REGISTRO) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Constantes.RUTA_HOME) {
            HomeScreen(
                onNavigateToPerfumeDetail = { perfumeId ->
                    navController.navigate("${Constantes.RUTA_DETALLE_PERFUME.replace("{perfumeId}", perfumeId.toString())}")
                },
                onNavigateToCart = {
                    navController.navigate(Constantes.RUTA_CARRITO)
                },
                onNavigateToProfile = {
                    navController.navigate(Constantes.RUTA_PERFIL)
                },
                onNavigateToAddPerfume = {
                    navController.navigate(Constantes.RUTA_AGREGAR_PERFUME)
                }
            )
        }
        
        composable(Constantes.RUTA_DETALLE_PERFUME) { backStackEntry ->
            val perfumeId = backStackEntry.arguments?.getString("perfumeId")?.toLongOrNull()
            if (perfumeId != null) {
                PerfumeDetailScreen(
                    perfumeId = perfumeId,
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onNavigateToCart = {
                        navController.navigate(Constantes.RUTA_CARRITO)
                    }
                )
            }
        }
        
        composable(Constantes.RUTA_CARRITO) {
            CartScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToHome = {
                    navController.navigate(Constantes.RUTA_HOME) {
                        popUpTo(Constantes.RUTA_CARRITO) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Constantes.RUTA_PERFIL) {
            ProfileScreen(
                authViewModel = authViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToLogin = {
                    navController.navigate(Constantes.RUTA_LOGIN) {
                        popUpTo(Constantes.RUTA_PERFIL) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Constantes.RUTA_AGREGAR_PERFUME) {
            AddPerfumeScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
