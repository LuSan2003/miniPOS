package com.example.minipos.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.minipos.data.TransactionRepository
import com.example.minipos.ui.screens.HistoryScreen
import com.example.minipos.ui.screens.PaymentScreen
import com.example.minipos.ui.viewmodel.ViewModelFactory

@Composable
fun AppNavigation(
    repository: TransactionRepository,
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = "payment") {
        composable("payment") {
            val viewModel = viewModel(
                factory = ViewModelFactory(repository),
                modelClass = com.example.minipos.ui.viewmodel.PaymentViewModel::class.java
            )
            PaymentScreen(viewModel = viewModel) {
                navController.navigate("history")
            }
        }
        composable("history") {
            val viewModel = viewModel(
                factory = ViewModelFactory(repository),
                modelClass = com.example.minipos.ui.viewmodel.HistoryViewModel::class.java
            )
            HistoryScreen(viewModel = viewModel) {
                navController.popBackStack()
            }
        }
    }
}
