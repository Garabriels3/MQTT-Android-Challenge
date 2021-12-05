package com.br.data.repository

import com.br.data.datasource.remote.championslist.SubscribeListRemoteDataSource
import com.br.data.response.ChampionResponse
import com.br.domain.entity.Champion
import com.br.domain.repository.SubscribeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class SubscribeRepositoryImpl(private val subscribeListRemoteDataSource: SubscribeListRemoteDataSource) :
    SubscribeRepository {
    override fun onMessageArrived(): Flow<List<Champion>?> {
        return subscribeListRemoteDataSource.onMessageArrived()
            .map { list ->
                list?.map {
                    it.mapToChampionDomain()
                }
            }.flowOn(Dispatchers.Default)
    }

    override fun subscribeOnTopic(topic: String): Flow<Unit> {
        return subscribeListRemoteDataSource.subscribeOnTopic(topic)
    }

    override fun unsubscribeOnTopic(topic: String): Flow<Unit> {
        return subscribeListRemoteDataSource.unsubscribeOnTopic(topic)
    }
}

fun ChampionResponse.mapToChampionDomain(): Champion {
    return Champion(
        squadName = champion,
        titleYear = cupYear.toString()
    )
}