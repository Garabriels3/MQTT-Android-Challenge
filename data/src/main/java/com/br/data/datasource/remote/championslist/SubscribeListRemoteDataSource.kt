package com.br.data.datasource.remote.championslist

import com.br.data.response.ChampionResponse
import kotlinx.coroutines.flow.Flow

interface SubscribeListRemoteDataSource {
    fun onMessageArrived(): Flow<List<ChampionResponse>?>
    fun subscribeOnTopic(topic: String): Flow<Unit>
    fun unsubscribeOnTopic(topic: String): Flow<Unit>
}