package com.br.data.datasource.remote.publish

import com.br.data.mqttclient.MqttClient
import kotlinx.coroutines.flow.Flow

class PublishMessageRemoteDataSourceImpl(private val mqttClient: MqttClient) :
    PublishMessageRemoteDataSource {

    override fun publishMessage(topic: String, msg: String): Flow<Unit> {
        return mqttClient.publish(
            topic,
            msg
        )
    }
}