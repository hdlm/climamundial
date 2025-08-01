package com.example.climamundial.di

import com.example.climamundial.data.repositories.ClimaRepository
import com.example.climamundial.data.repositories.ClimaRepositoryImpl
import com.example.climamundial.data.repositories.GeoRepository
import com.example.climamundial.data.repositories.GeoRepositoryImpl
import com.example.climamundial.networking.RetrofitHelper
import com.example.climamundial.networking.RetrofitHelperImpl
import com.example.climamundial.networking.services.ClimaApiService
import com.example.climamundial.networking.services.GeocodingApiService
import com.example.climamundial.presentation.presenters.ClimateViewModel
import com.example.climamundial.presentation.usecase.ClimaInfoUseCase
import com.example.climamundial.presentation.usecase.ClimaInfoUseCaseImpl
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Modules {
    val appModule = module {
        single<RetrofitHelper> { RetrofitHelperImpl() }

        factory<GeoRepository> { GeoRepositoryImpl(get()) }
        factory<ClimaRepository> { ClimaRepositoryImpl(get()) }
        factory<ClimaInfoUseCase> { ClimaInfoUseCaseImpl() }

        viewModel { ClimateViewModel(get(), get()) }
    }

    val instrumentedTestModule = module {
        single<RetrofitHelper> { RetrofitHelperImpl() }
        factory<ClimaRepository> { ClimaRepositoryImpl(get()) }
        factory<ClimaInfoUseCase> { ClimaInfoUseCaseImpl() }
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