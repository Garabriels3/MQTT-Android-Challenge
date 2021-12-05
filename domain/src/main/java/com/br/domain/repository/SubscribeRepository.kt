package com.br.domain.repository

import com.br.domain.entity.Champion
import kotlinx.coroutines.flow.Flow

interface SubscribeRepository {
    fun onMessageArrived(): Flow<List<Champion>?>
    fun subscribeOnTopic(topic: String): Flow<Unit>
    fun unsubscribeOnTopic(topic: String): Flow<Unit>
}