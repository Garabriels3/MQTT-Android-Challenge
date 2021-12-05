package com.br.data.mqttclient

import kotlinx.coroutines.flow.Flow
import org.eclipse.paho.client.mqttv3.IMqttActionListener

interface MqttClient {
    fun connect(
    ): Flow<Unit>

    fun onMessageArrived(): Flow<String>
    fun subscribe(
        topic: String,
    ): Flow<Unit>

    fun unsubscribe(
        topic: String,
    ): Flow<Unit>

    fun publish(
        topic: String,
        msg: String,
    ): Flow<Unit>
}