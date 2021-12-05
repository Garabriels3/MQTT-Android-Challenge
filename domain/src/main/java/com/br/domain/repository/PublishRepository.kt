package com.br.domain.repository

import kotlinx.coroutines.flow.Flow

interface PublishRepository {
    fun publishMessage(topic: String, msg: String): Flow<Unit>
}