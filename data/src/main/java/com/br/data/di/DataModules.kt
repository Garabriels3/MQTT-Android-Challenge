package com.br.data.di

import com.br.data.datasource.remote.championslist.SubscribeListRemoteDataSource
import com.br.data.datasource.remote.championslist.SubscribeListRemoteDataSourceImpl
import com.br.data.datasource.remote.connectmqtt.ConnectMqttRemoteDataSource
import com.br.data.datasource.remote.connectmqtt.ConnectMqttRemoteDataSourceImpl
import com.br.data.datasource.remote.publish.PublishMessageRemoteDataSource
import com.br.data.datasource.remote.publish.PublishMessageRemoteDataSourceImpl
import com.br.data.mqttclient.MqttClient
import com.br.data.mqttclient.MqttClientImpl
import com.br.data.repository.ConnectMqttRepositoryImpl
import com.br.data.repository.PublishRepositoryImpl
import com.br.data.repository.SubscribeRepositoryImpl
import com.br.domain.repository.ConnectMqttRepository
import com.br.domain.repository.PublishRepository
import com.br.domain.repository.SubscribeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.eclipse.paho.client.mqttv3.MqttAsyncClient
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence
import org.koin.dsl.module

private const val SERVER_URI = "tcp://io.adafruit.com:1883"

object DataModules {
    @ExperimentalCoroutinesApi
    val data = module {
        single<MqttClient> { MqttClientImpl(MqttAsyncClient(SERVER_URI, "", MemoryPersistence())) }
        factory<SubscribeListRemoteDataSource> { SubscribeListRemoteDataSourceImpl(get()) }
        factory<PublishRepository> { PublishRepositoryImpl(get()) }
        factory<PublishMessageRemoteDataSource> { PublishMessageRemoteDataSourceImpl(get()) }
        factory<ConnectMqttRemoteDataSource> { ConnectMqttRemoteDataSourceImpl(get()) }
        factory<ConnectMqttRepository> { ConnectMqttRepositoryImpl(get()) }
        factory<SubscribeRepository> { SubscribeRepositoryImpl(get()) }
    }
}