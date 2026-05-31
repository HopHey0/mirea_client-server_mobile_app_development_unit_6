package com.hophey.pract1.di

import android.app.Application
import com.hophey.pract1.data.network.HttpClient
import com.hophey.pract1.data.repository.PhotoRepositoryImpl
import com.hophey.pract1.domain.repository.PhotoRepository
import com.hophey.pract1.domain.useCase.GetPhotosListUseCase
import com.hophey.pract1.domain.useCase.SavePhotoUseCase
import com.hophey.pract1.presentation.ui.viewModel.PhotoGridViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.koin.plugin.module.dsl.viewModel

class App : Application(){
    val photosModule = module {
        val api = HttpClient.api
        single<PhotoRepository> { PhotoRepositoryImpl(api) }

        factory { GetPhotosListUseCase(get()) }
        factory { SavePhotoUseCase() }

        viewModel { PhotoGridViewModel(get(), get()) }
    }


    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(photosModule)
        }
    }
}