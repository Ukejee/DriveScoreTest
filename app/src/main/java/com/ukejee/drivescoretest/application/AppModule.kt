package com.ukejee.drivescoretest.application

import com.ukejee.drivescoretest.creditScore.data.di.creditScoreDataModule
import com.ukejee.drivescoretest.creditScore.ui.di.creditScoreUiModule


val modules = listOf(
    creditScoreDataModule,
    creditScoreUiModule
)