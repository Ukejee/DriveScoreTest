package com.ukejee.drivescoretest.creditScore.ui.viewModels

import com.ukejee.drivescoretest.creditScore.data.DataSource
import com.ukejee.drivescoretest.creditScore.data.models.CoachingSummary
import com.ukejee.drivescoretest.creditScore.data.models.CreditScoreInfo
import com.ukejee.drivescoretest.creditScore.data.models.CreditScoreResponse
import com.ukejee.drivescoretest.creditScore.data.repository.CreditScoreRepositoryContract
import com.ukejee.drivescoretest.testHelpers.createCreditScoreResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CreditScoreViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @MockK
    private lateinit var repository: CreditScoreRepositoryContract

    private lateinit var viewModel: CreditScoreViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        Dispatchers.setMain(testDispatcher)
        viewModel = CreditScoreViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun whenGetCreditScoreIsCalledAndReturnsSuccess_uiStateIsUpdatedCorrectly() = runTest {
        // Given
        val successResource = DataSource.success(createCreditScoreResponse())
        coEvery { repository.getCreditScoreInformation() } returns flowOf(successResource)

        // When
        viewModel.getCreditScoreInformation()
        advanceUntilIdle()

        // Then
        val uiState = viewModel.uiState.value
        assertEquals(514, uiState.creditScore)
        assertEquals(700, uiState.maximumCreditScore)
        assertEquals(false, uiState.isLoading)
        assertNull(uiState.error)
    }

    @Test
    fun whenGetCreditScoreIsCalledAndReturnsLoading_uiStateShowsLoading() = runTest {
        // Given
        val loadingResource = DataSource.loading(null)
        coEvery { repository.getCreditScoreInformation() } returns flowOf(loadingResource)

        // When
        viewModel.getCreditScoreInformation()
        advanceUntilIdle()

        // Then
        val uiState = viewModel.uiState.value
        assertTrue(uiState.isLoading)
        assertNull(uiState.error)
    }

    @Test
    fun whenGetCreditScoreIsCalledAndReturnsError_uiStateShowsError() = runTest {
        // Given
        val errorMessage = "Failed to fetch data"
        val errorResource = DataSource.error(errorMessage, null)
        coEvery { repository.getCreditScoreInformation() } returns flowOf(errorResource)

        // When
        viewModel.getCreditScoreInformation()
        advanceUntilIdle()

        // Then
        val uiState = viewModel.uiState.value
        assertEquals(false, uiState.isLoading)
        assertNotNull(uiState.error)
        assertEquals(errorMessage, uiState.error)
    }
}
