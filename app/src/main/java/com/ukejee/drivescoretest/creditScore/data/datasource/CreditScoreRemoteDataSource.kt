package com.ukejee.drivescoretest.creditScore.data.datasource

import com.ukejee.drivescoretest.creditScore.data.models.CreditScoreResponse

interface CreditScoreRemoteDataSource {

    suspend fun getCreditScoreInformation(): CreditScoreResponse
}