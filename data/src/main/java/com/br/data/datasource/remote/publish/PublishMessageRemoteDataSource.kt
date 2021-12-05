package com.br.data.datasource.remote.publish

import kotlinx.coroutines.flow.Flow

interface PublishMessageRemoteDataSource {
    fun publishMessage(topic: String, msg: String): Flow<Unit>
}