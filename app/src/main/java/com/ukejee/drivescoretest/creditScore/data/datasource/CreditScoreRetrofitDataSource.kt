package com.ukejee.drivescoretest.creditScore.data.datasource

import com.ukejee.drivescoretest.creditScore.data.models.CreditScoreResponse
import com.ukejee.drivescoretest.creditScore.data.network.CreditScoreServices

class CreditScoreRetrofitDataSource(
    val service: CreditScoreServices
) : CreditScoreRemoteDataSource {

    override suspend fun getCreditScoreInformation(): CreditScoreResponse {
        return service.getCreditScoreInformation()
    }
}