package com.br.domain.usecase.messagearrived

import com.br.domain.entity.Champion
import kotlinx.coroutines.flow.Flow

interface SubscribeUseCase {
    fun messageArrived(): Flow<List<Champion>?>
    fun subscribeOnTopic(topic: String): Flow<Unit>
    fun unsubscribeOnTopic(topic: String): Flow<Unit>
}