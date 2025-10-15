package com.ukejee.drivescoretest.creditScore.data.repository

import app.cash.turbine.test
import com.ukejee.drivescoretest.creditScore.data.Status
import com.ukejee.drivescoretest.creditScore.data.datasource.CreditScoreRemoteDataSource
import com.ukejee.drivescoretest.creditScore.data.models.CreditScoreInfo
import com.ukejee.drivescoretest.testHelpers.createCreditScoreResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


class CreditScoreRepositoryTest {

    @MockK
    private lateinit var remoteDataSource: CreditScoreRemoteDataSource

    private lateinit var repository: CreditScoreRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        repository = CreditScoreRepository(remoteDataSource)
    }

    @Test
    fun whenGetCreditScoreInformationIsSuccessfulItEmitsLoadingThenSuccess() = runTest {
        // Given
        val mockResponse = createCreditScoreResponse(
            creditScoreInfo = CreditScoreInfo(
                score = 550,
                maxScoreValue = 700,
                minScoreValue = 0
            )
        )
        coEvery { remoteDataSource.getCreditScoreInformation() } returns mockResponse

        // When
        repository.getCreditScoreInformation().test {
            // Then
            val loadingState = awaitItem()
            assertTrue(loadingState.status == Status.LOADING)

            val successState = awaitItem()
            assertEquals(mockResponse, successState.data)

            awaitComplete()
        }
    }

    @Test
    fun whenGetCreditScoreInformationThrowsExceptionItEmitsLoadingThenError() = runTest {
        // Given
        val errorMessage = "Network request failed"
        coEvery { remoteDataSource.getCreditScoreInformation() } throws RuntimeException(errorMessage)

        // When
        repository.getCreditScoreInformation().test {
            // Then
            val loadingState = awaitItem()
            assertTrue(loadingState.status == Status.LOADING)

            val errorState = awaitItem()
            assertEquals(errorMessage, errorState.message)
            assertNull(errorState.data)

            awaitComplete()
        }
    }
}