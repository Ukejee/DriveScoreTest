package com.ukejee.drivescoretest.creditScore.data.models

import com.google.gson.annotations.SerializedName

data class CreditScoreResponse(
    val accountIDVStatus: String? = null,
    val augmentedCreditScore: Int? = null,
    val coachingSummary: CoachingSummary? = null,
    @SerializedName("creditReportInfo")
    val creditScoreInfo: CreditScoreInfo? = null,
    val dashboardStatus: String? = null,
    val personaType: String? = null
)