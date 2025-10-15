package com.ukejee.drivescoretest.creditScore.data.repository

import com.ukejee.drivescoretest.creditScore.data.DataSource
import com.ukejee.drivescoretest.creditScore.data.models.CreditScoreResponse
import kotlinx.coroutines.flow.Flow

interface CreditScoreRepositoryContract {

    suspend fun getCreditScoreInformation(): Flow<DataSource<CreditScoreResponse>>
}