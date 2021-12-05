package com.br.domain.repository

import kotlinx.coroutines.flow.Flow

interface ConnectMqttRepository {
    fun connectMqtt(): Flow<Unit>
}