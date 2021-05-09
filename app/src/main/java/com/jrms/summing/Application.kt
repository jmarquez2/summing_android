package com.jrms.summing

import android.app.Application
import com.jrms.summing.repositories.WebServiceRepository
import com.jrms.summing.ui.addSpend.AddSpendViewModel
import com.jrms.summing.ui.home.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

val module = module {
    factory { AddSpendViewModel(get(), get()) }
    factory { HomeViewModel(get(), get()) }
    single { WebServiceRepository(get()) }
}

class Application : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(this@Application)
            modules(module)
        }
    }
}