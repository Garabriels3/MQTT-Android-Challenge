package com.br.data.datasource.remote.connectmqtt

import com.br.data.mqttclient.MqttClient
import kotlinx.coroutines.flow.Flow

class ConnectMqttRemoteDataSourceImpl(private val mqttClient: MqttClient) :
    ConnectMqttRemoteDataSource {

    override fun connectMqtt(): Flow<Unit> {
        return mqttClient.connect()
    }
}