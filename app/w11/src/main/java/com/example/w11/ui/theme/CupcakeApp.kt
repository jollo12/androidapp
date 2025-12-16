package com.example.w11.ui.theme

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.cupcake.navigation.CupcakeScreen
import com.example.cupcake.screen.FlavorScreen
import com.example.cupcake.screen.PickupScreen
import com.example.cupcake.screen.StartScreen
import com.example.cupcake.screen.SummaryScreen
import com.example.cupcake.viewmodel.OrderViewModel

@Composable
fun CupcakeApp(
    viewModel: OrderViewModel
) {
    // ✅ NavController 생성
    val navController = rememberNavController()

    // ✅ NavHost 설정
    NavHost(
        navController = navController,
        startDestination = CupcakeScreen.Start.route
    ) {
        // Start Screen
        composable(CupcakeScreen.Start.route) {
            StartScreen(
                onNext = { navController.navigate(CupcakeScreen.Flavor.route) }
            )
        }

        // Flavor Screen
        composable(CupcakeScreen.Flavor.route) {
            FlavorScreen(
                viewModel = viewModel,
                onNext = { navController.navigate(CupcakeScreen.Pickup.route) },
                onBack = { navController.popBackStack() }
            )
        }

        // Pickup Screen
        composable(CupcakeScreen.Pickup.route) {
            PickupScreen(
                viewModel = viewModel,
                onNext = { navController.navigate(CupcakeScreen.Summary.route) },
                onBack = { navController.popBackStack() }
            )
        }

        // Summary Screen
        composable(CupcakeScreen.Summary.route) {
            SummaryScreen(
                viewModel = viewModel,
                onRestart = {
                    viewModel.resetOrder()
                    navController.popBackStack(CupcakeScreen.Start.route, false)
                }
            )
        }
    }
}
