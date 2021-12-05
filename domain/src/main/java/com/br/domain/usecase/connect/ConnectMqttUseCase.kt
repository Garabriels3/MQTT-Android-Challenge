package com.br.domain.usecase.connect

import kotlinx.coroutines.flow.Flow

interface ConnectMqttUseCase {
    fun connectMqtt(): Flow<Unit>
}