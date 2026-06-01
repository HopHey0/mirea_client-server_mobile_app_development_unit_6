package com.hophey.pract3.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hophey.pract3.domain.usecase.GetTokenUseCase
import com.hophey.pract3.presentation.screen.LoginScreen
import com.hophey.pract3.presentation.screen.UserDetailScreen
import com.hophey.pract3.presentation.screen.UsersListScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object UsersList : Screen("users_list")
    object UserDetail : Screen("user_detail/{userId}") {
        fun createRoute(userId: Int) = "user_detail/$userId"
    }
}

@Composable
fun AppNavGraph(
    getTokenUseCase: GetTokenUseCase = koinInject()
) {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    var startDestination by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        val token = getTokenUseCase()
        startDestination = if (token != null) Screen.UsersList.route else Screen.Login.route
    }

    if (startDestination == null) return

    NavHost(
        navController = navController,
        startDestination = startDestination!!
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.UsersList.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.UsersList.route) {
            UsersListScreen(
                onUserClick = { userId ->
                    navController.navigate(Screen.UserDetail.createRoute(userId))
                },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
            )
        }
        composable(
            route = Screen.UserDetail.route,
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: return@composable
            UserDetailScreen(
                userId = userId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}