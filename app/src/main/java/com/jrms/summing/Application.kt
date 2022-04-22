package com.jrms.summing

import android.app.Application
import com.jrms.summing.repositories.SessionRepository
import com.jrms.summing.repositories.SharedPreferencesRepository
import com.jrms.summing.repositories.SpendRepository
import com.jrms.summing.repositories.WebServiceRepository
import com.jrms.summing.ui.spend.detail.SpendDataViewModel
import com.jrms.summing.ui.spend.list.SpendListViewModel
import com.jrms.summing.ui.location.LocationViewModel
import com.jrms.summing.ui.login.LoginViewModel
import com.jrms.summing.viewmodel.MainActivityViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

val module = module {
    viewModel { SpendDataViewModel(get(), get()) }
    viewModel { SpendListViewModel(get(), get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { LocationViewModel(get()) }
    viewModel { MainActivityViewModel(get()) }
    single {SpendRepository(get(), get())}
    single { WebServiceRepository(get()) }
    single {SharedPreferencesRepository(get())}
    single {SessionRepository(get(), get ())}
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