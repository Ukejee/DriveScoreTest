package com.ukejee.drivescoretest.creditScore.data.network

import com.ukejee.drivescoretest.creditScore.data.models.CreditScoreResponse
import retrofit2.http.GET

interface CreditScoreServices {

    @GET("endpoint.json")
    suspend fun getCreditScoreInformation(): CreditScoreResponse
}
