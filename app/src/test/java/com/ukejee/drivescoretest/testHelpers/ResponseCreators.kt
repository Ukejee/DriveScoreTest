package com.ukejee.drivescoretest.testHelpers

import com.ukejee.drivescoretest.creditScore.data.models.CoachingSummary
import com.ukejee.drivescoretest.creditScore.data.models.CreditScoreInfo
import com.ukejee.drivescoretest.creditScore.data.models.CreditScoreResponse

fun createCreditScoreResponse(
    accountIDVStatus: String? = "PASS",
    augmentedCreditScore: Int? = null,
    coachingSummary: CoachingSummary? = CoachingSummary(
        activeChat = false,
        activeTodo = false,
        numberOfCompletedTodoItems = 0,
        numberOfTodoItems = 0,
        selected = true
    ),
    creditScoreInfo: CreditScoreInfo? = CreditScoreInfo(
        score = 514,
        maxScoreValue = 700,
        minScoreValue = 0
    ),
    dashboardStatus: String? = "PASS",
    personaType: String? = "INEXPERIENCED"
): CreditScoreResponse {
    return CreditScoreResponse(
        accountIDVStatus = accountIDVStatus,
        augmentedCreditScore = augmentedCreditScore,
        coachingSummary = coachingSummary,
        creditScoreInfo = creditScoreInfo,
        dashboardStatus = dashboardStatus,
        personaType = personaType
    )
}