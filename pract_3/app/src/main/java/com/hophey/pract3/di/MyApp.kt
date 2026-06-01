package com.hophey.pract3.di

import android.app.Application
import com.hophey.pract3.data.network.Api
import com.hophey.pract3.data.network.HttpClient
import com.hophey.pract3.data.repository.AuthRepositoryImpl
import com.hophey.pract3.data.repository.UsersRepositoryImpl
import com.hophey.pract3.domain.repository.AuthRepository
import com.hophey.pract3.domain.repository.UsersRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import kotlin.math.sin

class MyApp: Application() {
    val authModule = module {
        single { HttpClient.provideHttpClient() }

        single { Api(get()) }

        single<UsersRepository> { UsersRepositoryImpl(get(), get()) }
        single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(authModule)
        }
    }
}