package com.br.data.datasource.remote.connectmqtt

import io.reactivex.Completable
import kotlinx.coroutines.flow.Flow

interface ConnectMqttRemoteDataSource {
    fun connectMqtt(): Flow<Unit>
}