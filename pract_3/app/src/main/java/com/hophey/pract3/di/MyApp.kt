package com.hophey.pract3.di

import android.app.Application
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hophey.pract3.data.datastore.TokenDataStore
import com.hophey.pract3.data.network.Api
import com.hophey.pract3.data.network.HttpClient
import com.hophey.pract3.data.repository.AuthRepositoryImpl
import com.hophey.pract3.data.repository.UsersRepositoryImpl
import com.hophey.pract3.domain.repository.AuthRepository
import com.hophey.pract3.domain.repository.UsersRepository
import com.hophey.pract3.domain.usecase.GetTokenUseCase
import com.hophey.pract3.domain.usecase.GetUserByIdUseCase
import com.hophey.pract3.domain.usecase.GetUsersUseCase
import com.hophey.pract3.domain.usecase.LoginUseCase
import com.hophey.pract3.domain.usecase.LogoutUseCase
import com.hophey.pract3.presentation.viewmodel.LoginViewModel
import com.hophey.pract3.presentation.viewmodel.UserDetailViewModel
import com.hophey.pract3.presentation.viewmodel.UsersListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModel

class MyApp: Application() {
    val authModule = module {
        single { HttpClient.provideHttpClient() }

        single { Api(get()) }
        single { TokenDataStore(get())}

        single<UsersRepository> { UsersRepositoryImpl(get(), get()) }
        single<AuthRepository> { AuthRepositoryImpl(get(), get()) }

        factory { GetTokenUseCase(get()) }
        factory { GetUserByIdUseCase(get()) }
        factory { GetUsersUseCase(get()) }
        factory { LoginUseCase(get()) }
        factory { LogoutUseCase(get()) }

        viewModel { LoginViewModel(get()) }
        viewModel { UsersListViewModel(get(), get()) }
        viewModel { UserDetailViewModel(get()) }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(authModule)
        }
    }
}