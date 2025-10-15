package com.ukejee.drivescoretest.creditScore.ui.di

import com.ukejee.drivescoretest.creditScore.ui.viewModels.CreditScoreViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val creditScoreUiModule = module {

    viewModel {
        CreditScoreViewModel(get())
    }
}