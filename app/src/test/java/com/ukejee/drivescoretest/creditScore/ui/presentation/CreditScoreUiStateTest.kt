package com.ukejee.drivescoretest.creditScore.ui.presentation

import com.ukejee.drivescoretest.creditScore.data.models.CreditScoreInfo
import com.ukejee.drivescoretest.testHelpers.createCreditScoreResponse
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class CreditScoreUiStateTest {
    @Test
    fun whenToCreditScoreStateIsCalledWithValidData_stateIsMappedCorrectly() {
        // Given a valid CreditReportInfo object
        val creditReportInfo = createCreditScoreResponse()

        // When the toCreditScoreState function is called
        val uiState = creditReportInfo.toCreditScoreState()

        // Then the resulting CreditScoreUiState should have the correct values
        assertEquals(514, uiState.creditScore)
        assertEquals(700, uiState.maximumCreditScore)
        assertEquals(false, uiState.isLoading)
        assertNull(uiState.error)
    }

    @Test
    fun whenToCreditScoreStateIsCalledWithNullValues_stateIsMappedWithDefaults() {
        // Given a CreditReportInfo object with null values for score and maxScoreValue
        val creditReportInfoWithNulls = createCreditScoreResponse(
            creditScoreInfo = CreditScoreInfo(
                score = null,
                maxScoreValue = null
            )
        )

        // When the toCreditScoreState function is called
        val uiState = creditReportInfoWithNulls.toCreditScoreState()

        // Then the score and maxScoreValue should be set to their default value of 0
        assertEquals(0, uiState.creditScore)
        assertEquals(0, uiState.maximumCreditScore)
        assertEquals(false, uiState.isLoading)
        assertNull(uiState.error)
    }

    @Test
    fun whenToCreditScoreStateIsCalledWithZeroValues_stateIsMappedCorrectly() {
        // Given a CreditReportInfo object with zero values
        val creditReportInfoWithZeros = createCreditScoreResponse(
            creditScoreInfo = CreditScoreInfo(
                score = 0,
                maxScoreValue = 0
            )
        )

        // When the toCreditScoreState function is called
        val uiState = creditReportInfoWithZeros.toCreditScoreState()

        // Then the zero values should be correctly reflected in the UI state
        assertEquals(0, uiState.creditScore)
        assertEquals(0, uiState.maximumCreditScore)
        assertEquals(false, uiState.isLoading)
        assertNull(uiState.error)
    }
}