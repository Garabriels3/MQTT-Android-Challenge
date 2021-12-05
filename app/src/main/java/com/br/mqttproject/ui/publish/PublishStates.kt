package com.br.mqttproject.ui.publish

data class PublishStates(
    val onSuccessPublish: String = "",
    val onErrorPublish: String = "",
    val onLoadingPublish: Boolean = false
)