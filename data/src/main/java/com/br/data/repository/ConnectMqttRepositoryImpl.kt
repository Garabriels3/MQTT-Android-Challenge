package com.br.data.repository

import com.br.data.datasource.remote.connectmqtt.ConnectMqttRemoteDataSource
import com.br.domain.repository.ConnectMqttRepository
import kotlinx.coroutines.flow.Flow

class ConnectMqttRepositoryImpl(private val connectMqttRemoteDataSource: ConnectMqttRemoteDataSource) :
    ConnectMqttRepository {
    override fun connectMqtt(): Flow<Unit> {
        return connectMqttRemoteDataSource.connectMqtt()
    }
}