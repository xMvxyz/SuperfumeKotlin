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
import com.SuperfumeKotlin.util.Constants

/**
 * Navegación principal de la aplicación Superfume
 * Define todas las rutas y transiciones entre pantallas
 */
@Composable
fun NavegacionSuperfume(navController: NavHostController) {
    val authViewModel: ViewModelAutenticacion = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = Constants.ROUTE_HOME
    ) {
        composable(Constants.ROUTE_LOGIN) {
            LoginScreen(
                viewModel = authViewModel,
                onNavigateToRegister = {
                    navController.navigate(Constants.ROUTE_REGISTER)
                },
                onNavigateToHome = {
                    navController.navigate(Constants.ROUTE_HOME) {
                        popUpTo(Constants.ROUTE_LOGIN) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            )
        }
        
        composable(Constants.ROUTE_REGISTER) {
            RegisterScreen(
                viewModel = authViewModel,
                onNavigateToLogin = {
                    navController.navigate(Constants.ROUTE_LOGIN)
                },
                onNavigateToHome = {
                    navController.navigate(Constants.ROUTE_HOME) {
                        popUpTo(Constants.ROUTE_LOGIN) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            )
        }
        
        composable(Constants.ROUTE_HOME) {
            HomeScreen(
                onNavigateToPerfumeDetail = { perfumeId ->
                    navController.navigate("${Constants.ROUTE_PERFUME_DETAIL.replace("{perfumeId}", perfumeId.toString())}")
                },
                onNavigateToCart = {
                    navController.navigate(Constants.ROUTE_CART)
                },
                onNavigateToProfile = {
                    navController.navigate(Constants.ROUTE_PROFILE)
                },
                onNavigateToAddPerfume = {
                    navController.navigate(Constants.ROUTE_ADD_PERFUME)
                }
            )
        }
        
        composable(Constants.ROUTE_PERFUME_DETAIL) { backStackEntry ->
            val perfumeId = backStackEntry.arguments?.getString("perfumeId")?.toLongOrNull()
            if (perfumeId != null) {
                PerfumeDetailScreen(
                    perfumeId = perfumeId,
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onNavigateToCart = {
                        navController.navigate(Constants.ROUTE_CART)
                    }
                )
            }
        }
        
        composable(Constants.ROUTE_CART) {
            CartScreen(
                authViewModel = authViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToHome = {
                    navController.navigate(Constants.ROUTE_HOME) {
                        popUpTo(Constants.ROUTE_CART) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.navigate(Constants.ROUTE_LOGIN) {
                        popUpTo(Constants.ROUTE_CART) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Constants.ROUTE_PROFILE) {
            ProfileScreen(
                authViewModel = authViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToLogin = {
                    navController.navigate(Constants.ROUTE_LOGIN) {
                        popUpTo(Constants.ROUTE_PROFILE) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Constants.ROUTE_ADD_PERFUME) {
            AddPerfumeScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
