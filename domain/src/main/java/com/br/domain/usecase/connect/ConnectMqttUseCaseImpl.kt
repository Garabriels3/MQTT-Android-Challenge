package com.br.domain.usecase.connect

import com.br.domain.repository.ConnectMqttRepository
import kotlinx.coroutines.flow.Flow

class ConnectMqttUseCaseImpl(private val connectMqttRepository: ConnectMqttRepository) : ConnectMqttUseCase {
    override fun connectMqtt(): Flow<Unit> {
        return connectMqttRepository.connectMqtt()
    }
}