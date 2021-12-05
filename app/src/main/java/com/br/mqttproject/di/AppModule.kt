package com.br.mqttproject.di

import com.br.mqttproject.ui.home.HomeViewModel
import com.br.mqttproject.ui.publish.PublishViewModel
import com.br.mqttproject.ui.receive.SubscribeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModule {

    val presentation = module {
        viewModel { PublishViewModel(get()) }
        viewModel { HomeViewModel(get()) }
        viewModel { SubscribeViewModel(get()) }
    }
}