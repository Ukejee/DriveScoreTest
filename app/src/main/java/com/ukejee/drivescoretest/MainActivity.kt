package com.ukejee.drivescoretest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.ukejee.drivescoretest.creditScore.ui.viewModels.CreditScoreViewModel
import com.ukejee.drivescoretest.ui.theme.DriveScoreTestTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val creditScoreViewModel : CreditScoreViewModel by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DriveScoreTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    val uiState = creditScoreViewModel.uiState.collectAsState()
                    RootNavigationGraph(
                        creditScoreViewModel,
                        navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
