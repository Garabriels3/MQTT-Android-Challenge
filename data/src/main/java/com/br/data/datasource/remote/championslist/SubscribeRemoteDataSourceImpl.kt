package com.br.data.datasource.remote.championslist

import com.beust.klaxon.Klaxon
import com.br.data.mqttclient.MqttClient
import com.br.data.response.ChampionResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val KLAXON_PARSE_ERROR = "Error Klaxon Parse array to Object"

class SubscribeListRemoteDataSourceImpl(
    private val mqttClient: MqttClient
) : SubscribeListRemoteDataSource {

    override fun onMessageArrived(): Flow<List<ChampionResponse>?> {
        return mqttClient.onMessageArrived()
            .map {
                it.fromJson()
            }
    }

    override fun subscribeOnTopic(topic: String): Flow<Unit> {
        return mqttClient.subscribe(topic)
    }

    override fun unsubscribeOnTopic(topic: String): Flow<Unit> {
        return mqttClient.unsubscribe(topic)
    }
}

private inline fun <reified T> String.fromJson(): List<T> {
    val klaxon = Klaxon()
    return klaxon.parseArray(this)
        ?: throw Exception(KLAXON_PARSE_ERROR)
}