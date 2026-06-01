package com.hophey.pract2.di

import android.app.Application
import com.hophey.pract2.data.network.HttpClientFactory

import org.koin.dsl.module


class App: Application(){
    val nobelPrizesModule = module {
        single { HttpClientFactory.create() }


    }
}