package com.hophey.pract2.di

import android.app.Application
import com.hophey.pract2.data.network.HttpClientFactory
import com.hophey.pract2.data.network.api.NobelPrizesApi
import com.hophey.pract2.data.repository.NobelPrizesRepositoryImpl
import com.hophey.pract2.domain.repository.NobelPrizesRepository
import com.hophey.pract2.domain.useCase.GetNobelPrizesByYearAndCategoryUseCase
import com.hophey.pract2.presentation.viewModel.NobelPrizesViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel

import org.koin.dsl.module
import org.koin.plugin.module.dsl.module
import org.koin.plugin.module.dsl.viewModel


class App: Application(){
    val nobelPrizesModule = module {
        single { HttpClientFactory.create() }

        single { NobelPrizesApi(get()) }

        single<NobelPrizesRepository> { NobelPrizesRepositoryImpl(get()) }

        factory { GetNobelPrizesByYearAndCategoryUseCase(get()) }

        viewModel { NobelPrizesViewModel(get()) }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(nobelPrizesModule)
        }
    }
}