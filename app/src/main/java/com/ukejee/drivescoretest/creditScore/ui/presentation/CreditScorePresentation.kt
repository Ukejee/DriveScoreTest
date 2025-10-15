package com.ukejee.drivescoretest.creditScore.ui.presentation

import com.ukejee.drivescoretest.creditScore.data.models.CreditScoreResponse

data class CreditScoreUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val creditScore: Int? = null,
    val maximumCreditScore: Int? = null
)

fun CreditScoreResponse.toCreditScoreState() = CreditScoreUiState(
    creditScore = this.creditScoreInfo?.score ?: 0,
    maximumCreditScore = this.creditScoreInfo?.maxScoreValue ?: 0
)