package com.ukejee.drivescoretest.creditScore.ui.compose

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.ukejee.drivescoretest.creditScore.ui.presentation.CreditScoreUiState
import org.junit.Rule
import org.junit.Test

class CreditScoreScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenScreenIsInLoadingState_circularProgressIndicatorIsShown() {
        // Given
        val loadingState = CreditScoreUiState(isLoading = true)

        // When
        composeTestRule.setContent {
            CreditScoreScreen(loadingState)
        }

        // Then
        composeTestRule.onNodeWithText("Your credit score is").assertDoesNotExist()
        composeTestRule.onNodeWithText("Something went wrong").assertDoesNotExist()
        composeTestRule.onNodeWithTag(SCREEN_LOADER_TAG).assertIsDisplayed()
    }

    @Test
    fun whenDataIsLoadedSuccessfully_creditScoreInfoIsDisplayed() {
        // Given the UI state is success with valid data
        val successState = CreditScoreUiState(
            creditScore = 550,
            maximumCreditScore = 700,
            isLoading = false
        )

        // When the CreditScoreScreen is displayed
        composeTestRule.setContent {
            CreditScoreScreen(successState)
        }

        // Then the credit score information should be visible
        composeTestRule.onNodeWithText("Your credit score is").assertIsDisplayed()
        composeTestRule.onNodeWithText("550").assertIsDisplayed()
        composeTestRule.onNodeWithText("out of 700").assertIsDisplayed()
        composeTestRule.onNodeWithTag(SCREEN_LOADER_TAG).assertIsNotDisplayed()
    }

    @Test
    fun whenAnErrorOccurs_errorMessageIsDisplayed() {
        // Given
        val errorMessage = "Could not fetch score"
        val errorState = CreditScoreUiState(
            isLoading = false,
            error = errorMessage
        )

        // When
        composeTestRule.setContent {
            CreditScoreScreen(errorState)
        }

        // Then
        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
        composeTestRule.onNodeWithText("Your credit score is").assertDoesNotExist()
    }
}