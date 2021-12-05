package com.br.mqttproject.ui.receive

import com.br.domain.entity.Champion

data class SubscribeStates(
    val onSuccessMessageArrived: List<Champion>? = null,
    val onSuccessSubscribe: Boolean = false,
    val onSuccessUnsubscribe: Boolean = false,
    val onError: Boolean = false,
    val onLoadingSubscribe: Boolean = false,
    val onLoadingMessageArriving: Boolean = false
)
