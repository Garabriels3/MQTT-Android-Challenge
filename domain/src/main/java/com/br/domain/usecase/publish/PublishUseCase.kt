package com.br.domain.usecase.publish

import kotlinx.coroutines.flow.Flow

interface PublishUseCase {
    fun publishMessage(topic: String, msg: String): Flow<Unit>
}