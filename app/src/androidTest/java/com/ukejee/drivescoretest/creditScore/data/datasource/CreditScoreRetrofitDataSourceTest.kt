package com.ukejee.drivescoretest.creditScore.data.datasource

import androidx.test.platform.app.InstrumentationRegistry
import com.ukejee.drivescoretest.application.MainApplication
import com.ukejee.drivescoretest.creditScore.data.models.CoachingSummary
import com.ukejee.drivescoretest.creditScore.data.models.CreditScoreInfo
import com.ukejee.drivescoretest.creditScore.data.models.CreditScoreResponse
import com.ukejee.drivescoretest.creditScore.data.network.CreditScoreServices
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import org.junit.After
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.Assert.assertEquals
import org.junit.Test
import java.nio.charset.StandardCharsets

class CreditScoreRetrofitDataSourceTest {

    private val mockWebServer = MockWebServer()

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CreditScoreServices::class.java)

    private val sut = CreditScoreRetrofitDataSource(api)

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getCreditScoreInformation_with200Response_returnsProperlyMappedObject() {
        mockWebServer.enqueueResponse("success_response.json", 200)

        runBlocking {
            val actual = sut.getCreditScoreInformation()

            val expectedResponse = CreditScoreResponse(
                accountIDVStatus = "PASS",
                creditScoreInfo = CreditScoreInfo(
                    score = 514,
                    scoreBand = 4,
                    clientRef = "CS-SED-655426-708782",
                    status = "MATCH",
                    maxScoreValue = 700,
                    minScoreValue = 0,
                    monthsSinceLastDefaulted = -1,
                    hasEverDefaulted = false,
                    monthsSinceLastDelinquent = 1,
                    hasEverBeenDelinquent = true,
                    percentageCreditUsed = 44,
                    percentageCreditUsedDirectionFlag = 1,
                    changedScore = 0,
                    currentShortTermDebt = 13758,
                    currentShortTermNonPromotionalDebt = 13758,
                    currentShortTermCreditLimit = 30600,
                    currentShortTermCreditUtilisation = 44,
                    changeInShortTermDebt = 549,
                    currentLongTermDebt = 24682,
                    currentLongTermNonPromotionalDebt = 24682,
                    currentLongTermCreditLimit = null,
                    currentLongTermCreditUtilisation = null,
                    changeInLongTermDebt = -327,
                    numPositiveScoreFactors = 9,
                    numNegativeScoreFactors = 0,
                    equifaxScoreBand = 4,
                    equifaxScoreBandDescription = "Excellent",
                    daysUntilNextReport = 9
                ),
                dashboardStatus = "PASS",
                personaType = "INEXPERIENCED",
                coachingSummary = CoachingSummary(
                    activeTodo = false,
                    activeChat = true,
                    numberOfTodoItems = 0,
                    numberOfCompletedTodoItems = 0,
                    selected = true
                ),
                augmentedCreditScore = null
            )

            assertEquals(expectedResponse, actual)
        }
    }

    @Test(expected = Exception::class)
    fun whenRequestFails_shouldThrowException() = runTest {
        // Given
        mockWebServer.enqueue(MockResponse().setResponseCode(500))

        // When
        sut.getCreditScoreInformation()

        // Then an exception should be thrown by Retrofit/OkHttp
    }

    private fun MockWebServer.enqueueResponse(fileName: String, code: Int) {
        val context = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as MainApplication
        val inputStream = context.assets.open(fileName)

        val source = inputStream.let { inputStream.source().buffer() }
        enqueue(
            MockResponse()
                .setResponseCode(code)
                .setBody(source.readString(StandardCharsets.UTF_8))
        )
    }
}