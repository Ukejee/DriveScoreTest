package com.ukejee.drivescoretest.creditScore.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.ukejee.drivescoretest.R
import androidx.compose.ui.unit.sp
import com.ukejee.drivescoretest.creditScore.ui.presentation.CreditScoreUiState
import com.ukejee.drivescoretest.creditScore.ui.viewModels.CreditScoreViewModel

const val SCREEN_LOADER_TAG = "SCREEN_LOADER_TAG"

@Composable
fun CreditScoreContainer(viewModel: CreditScoreViewModel) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getCreditScoreInformation()
    }

    CreditScoreScreen(uiState)
}

@Composable
fun CreditScoreScreen(uiState: CreditScoreUiState) {
    if (uiState.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
                    .testTag(SCREEN_LOADER_TAG)
            )
        }
    } else if (uiState.creditScore != null && uiState.maximumCreditScore != null) {
        CreditScoreInfoContent(
            uiState.creditScore,
            uiState.maximumCreditScore
        )
    } else {
        Message(uiState.error ?: "Something went wrong")
    }
}

@Composable
fun CreditScoreInfoContent(creditScore: Int, maxScore: Int) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressComponent(
            progress = creditScore.toFloat(),
            maxProgress = maxScore.toFloat(),
            line1 = stringResource(R.string.credit_score_intro_text),
            line2 = "$creditScore",
            line3 = stringResource(R.string.max_term_text, maxScore),
            progressColor1 = Color.Red,
            progressColor2 = Color.Yellow,
            progressColor3 = Color(0xFFFFA500),
        )
    }
}


@Composable
private fun Message(message: String) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = message,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}