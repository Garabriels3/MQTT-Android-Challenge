package com.br.data.repository

import com.br.data.datasource.remote.publish.PublishMessageRemoteDataSource
import com.br.domain.repository.PublishRepository
import kotlinx.coroutines.flow.Flow

class PublishRepositoryImpl(private val publishRemoteDataSource: PublishMessageRemoteDataSource) :
    PublishRepository {
    override fun publishMessage(topic: String, msg: String): Flow<Unit> {
        return publishRemoteDataSource.publishMessage(topic, msg)
    }
}