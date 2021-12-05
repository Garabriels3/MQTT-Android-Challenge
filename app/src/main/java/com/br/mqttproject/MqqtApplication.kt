package com.br.mqttproject

import android.app.Application
import com.br.mqttproject.di.AppComponent.getAllModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

@ExperimentalCoroutinesApi
class MqqtApplication : KoinComponent, Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.INFO)
            androidContext(this@MqqtApplication)
            modules(getAllModules())
        }
    }
}

