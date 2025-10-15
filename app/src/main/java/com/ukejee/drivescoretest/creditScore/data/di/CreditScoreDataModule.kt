package com.ukejee.drivescoretest.creditScore.data.di

import com.ukejee.drivescoretest.creditScore.data.datasource.CreditScoreRemoteDataSource
import com.ukejee.drivescoretest.creditScore.data.datasource.CreditScoreRetrofitDataSource
import com.ukejee.drivescoretest.creditScore.data.network.ApiConstants
import com.ukejee.drivescoretest.creditScore.data.network.CreditScoreServices
import com.ukejee.drivescoretest.creditScore.data.repository.CreditScoreRepository
import com.ukejee.drivescoretest.creditScore.data.repository.CreditScoreRepositoryContract
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun provideHttpClient(): OkHttpClient {
    return OkHttpClient
        .Builder()
        .readTimeout(ApiConstants.DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .connectTimeout(ApiConstants.DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .build()
}


fun provideConverterFactory(): GsonConverterFactory =
    GsonConverterFactory.create()


fun provideRetrofit(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(ApiConstants.BASE_ENDPOINT)
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .build()
}

fun provideService(retrofit: Retrofit): CreditScoreServices =
    retrofit.create(CreditScoreServices::class.java)

val creditScoreDataModule = module {
    single {
        provideHttpClient()
    }

    single {
        provideConverterFactory()
    }

    single {
        provideRetrofit(get(), get())
    }

    single {
        provideService(get())
    }

    factory<CreditScoreRemoteDataSource> {
        CreditScoreRetrofitDataSource(get())
    }

    single<CreditScoreRepositoryContract> {
        CreditScoreRepository(get())
    }
}