package com.br.domain.usecase.messagearrived

import com.br.domain.entity.Champion
import com.br.domain.repository.SubscribeRepository
import kotlinx.coroutines.flow.Flow

class SubscribeUseCaseImpl(private val subscribeRepository: SubscribeRepository) :
    SubscribeUseCase {

    override fun messageArrived(): Flow<List<Champion>?> {
        return subscribeRepository.onMessageArrived()
    }

    override fun subscribeOnTopic(topic: String): Flow<Unit> {
        return subscribeRepository.subscribeOnTopic(topic)
    }

    override fun unsubscribeOnTopic(topic: String): Flow<Unit> {
        return subscribeRepository.unsubscribeOnTopic(topic)
    }
}