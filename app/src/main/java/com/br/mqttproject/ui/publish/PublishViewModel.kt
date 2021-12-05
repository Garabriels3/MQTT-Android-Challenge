package com.br.mqttproject.ui.publish

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.domain.usecase.publish.PublishUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

private const val TOPIC = "gabrixx/feeds/test"
private const val ERROR = "Error publishing data!"
private const val SUCCESS = "Data published successfully!"

class PublishViewModel(
    private val publishUseCase: PublishUseCase
) : ViewModel() {

    val state: StateFlow<PublishStates> get() = _state

    private val _state: MutableStateFlow<PublishStates> = MutableStateFlow(PublishStates())

    private val internalState = PublishStates()

    fun publish(msg: String) {
        viewModelScope.launch {
            publishUseCase.publishMessage(
                TOPIC,
                msg
            )
                .flowOn(Dispatchers.IO)
                .onStart {
                    _state.value = internalState.copy(onLoadingPublish = true)
                }
                .onCompletion {
                    _state.value = internalState.copy(onLoadingPublish = false)
                }
                .catch {
                    _state.value = internalState.copy(
                        onErrorPublish = ERROR
                    )
                }
                .collect {
                    _state.value = internalState.copy(
                        onSuccessPublish = SUCCESS,
                    )
                }
        }
    }
}