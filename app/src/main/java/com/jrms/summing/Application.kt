package com.jrms.summing

import android.app.Application
import com.jrms.summing.repositories.LoginRepository
import com.jrms.summing.repositories.SharedPreferencesRepository
import com.jrms.summing.repositories.SpendRepository
import com.jrms.summing.repositories.WebServiceRepository
import com.jrms.summing.ui.addSpend.AddSpendViewModel
import com.jrms.summing.ui.home.HomeViewModel
import com.jrms.summing.ui.login.LoginViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

val module = module {
    viewModel { AddSpendViewModel(get(), get()) }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { LoginViewModel(get()) }
    single {SpendRepository(get(), get())}
    single { WebServiceRepository(get()) }
    single {SharedPreferencesRepository(get())}
    single {LoginRepository(get(), get ())}
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