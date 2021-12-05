package com.br.domain.di

import com.br.domain.usecase.connect.ConnectMqttUseCase
import com.br.domain.usecase.connect.ConnectMqttUseCaseImpl
import com.br.domain.usecase.messagearrived.SubscribeUseCase
import com.br.domain.usecase.messagearrived.SubscribeUseCaseImpl
import com.br.domain.usecase.publish.PublishUseCase
import com.br.domain.usecase.publish.PublishUseCaseImpl
import org.koin.dsl.module

object DomainModules {
    val domain = module {
        factory<PublishUseCase> { PublishUseCaseImpl(get()) }
        factory<ConnectMqttUseCase> { ConnectMqttUseCaseImpl(get()) }
        factory<SubscribeUseCase> { SubscribeUseCaseImpl(get()) }
    }
}