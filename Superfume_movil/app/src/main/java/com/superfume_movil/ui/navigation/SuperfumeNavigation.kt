package com.superfume_movil.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.superfume_movil.ui.screens.admin.AddPerfumeScreen
import com.superfume_movil.ui.screens.auth.LoginScreen
import com.superfume_movil.ui.screens.auth.RegisterScreen
import com.superfume_movil.ui.screens.cart.CartScreen
import com.superfume_movil.ui.screens.home.HomeScreen
import com.superfume_movil.ui.screens.perfume.PerfumeDetailScreen
import com.superfume_movil.ui.screens.profile.ProfileScreen
import com.superfume_movil.util.Constantes

/**
 * Navegación principal de la aplicación Superfume
 * Define todas las rutas y transiciones entre pantallas
 */
@Composable
fun NavegacionSuperfume(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Constantes.RUTA_LOGIN
    ) {
        composable(Constantes.RUTA_LOGIN) {
            LoginScreen(
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
