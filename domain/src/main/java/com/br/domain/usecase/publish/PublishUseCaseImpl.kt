package com.br.domain.usecase.publish

import com.br.domain.repository.PublishRepository
import kotlinx.coroutines.flow.Flow

class PublishUseCaseImpl(private val publishRepository: PublishRepository) : PublishUseCase {
    override fun publishMessage(topic: String, msg: String): Flow<Unit> {
        return publishRepository.publishMessage(topic, msg)
    }
}