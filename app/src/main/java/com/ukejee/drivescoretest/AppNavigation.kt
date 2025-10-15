package com.ukejee.drivescoretest

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ukejee.drivescoretest.creditScore.ui.compose.CreditScoreContainer
import com.ukejee.drivescoretest.creditScore.ui.viewModels.CreditScoreViewModel

@Composable
fun RootNavigationGraph(viewModel: CreditScoreViewModel, navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.CreditScore.name,
        modifier = modifier
    ) {
        composable(Screen.CreditScore.name) {
            CreditScoreContainer(viewModel)
        }
    }
}

enum class Screen() {
    CreditScore
}