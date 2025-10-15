package com.ukejee.drivescoretest.creditScore.data.repository

import com.ukejee.drivescoretest.creditScore.data.DataSource
import com.ukejee.drivescoretest.creditScore.data.datasource.CreditScoreRemoteDataSource
import com.ukejee.drivescoretest.creditScore.data.models.CreditScoreResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CreditScoreRepository(
    private val remoteDataSource: CreditScoreRemoteDataSource
): CreditScoreRepositoryContract {

    override suspend fun getCreditScoreInformation(): Flow<DataSource<CreditScoreResponse>> {
        return flow {
            try {
                emit(DataSource.loading(null))
                val response = remoteDataSource.getCreditScoreInformation()
                emit(DataSource.success(response))
            } catch (e: Exception) {
                emit(DataSource.error(e.localizedMessage ?: "Something went wrong...", null))
            }
        }
    }
}