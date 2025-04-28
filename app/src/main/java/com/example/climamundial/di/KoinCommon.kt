package com.example.climamundial.di

import com.example.climamundial.data.repositories.ClimaRepository
import com.example.climamundial.data.repositories.ClimaRepositoryImpl
import com.example.climamundial.networking.RetrofitHelper
import com.example.climamundial.networking.RetrofitHelperImpl
import com.example.climamundial.presentation.presenters.ClimateViewModel
import com.example.climamundial.presentation.usecase.ClimateInfoUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object Modules {
    val appModule = module {
        single<RetrofitHelper> { RetrofitHelperImpl() }
        factory<ClimaRepository> { ClimaRepositoryImpl() }
        viewModel { ClimateViewModel() }
        factory { ClimateInfoUseCase() }
    }

    val unitTestModule = module {
//        Module.factory { Gson() }
//        Module.factory<RetrofitHelper> { RetrofitHelperImpl() }
//        Module.factory<BankAccountLocalRepository> { BankAccountLocalRepositoryImpl() }
//        Module.factory<UserLocalRepository> { UserLocalRepositoryImpl() }
//        Module.factory { UserInfoUseCase() }
//        Module.factory { AccountInfoUseCase() }
//        Module.factory { UserInfoDummyUseCase() }
//        Module.factory<BalanceLocalRepository> { BalanceLocalRepositoryImpl() }
    }
}