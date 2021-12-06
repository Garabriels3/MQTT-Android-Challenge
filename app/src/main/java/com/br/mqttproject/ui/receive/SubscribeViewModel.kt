package com.br.mqttproject.ui.receive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.domain.usecase.messagearrived.SubscribeUseCase
import kotlinx.coroutines.CoroutineDispatcher
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

class SubscribeViewModel(
    private val subscribeUseCase: SubscribeUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) :
    ViewModel() {

    val state: StateFlow<SubscribeStates> get() = _state

    private val _state: MutableStateFlow<SubscribeStates> = MutableStateFlow(SubscribeStates())

    private val internalState = SubscribeStates()

    fun onMessageArrived() {
        viewModelScope.launch {
            subscribeUseCase.messageArrived()
                .flowOn(dispatcher)
                .onStart { _state.value = internalState.copy(onLoadingMessageArriving = true) }
                .onCompletion {
                    _state.value = internalState.copy(onLoadingMessageArriving = false)
                }
                .catch {
                    _state.value = internalState.copy(onError = true)
                    onMessageArrived()
                }
                .collect {
                    _state.value = internalState.copy(onSuccessMessageArrived = it)
                }
        }
    }

    fun subscribeOnTopic() {
        viewModelScope.launch {
            subscribeUseCase.subscribeOnTopic(TOPIC)
                .flowOn(Dispatchers.IO)
                .onStart { _state.value = internalState.copy(onLoadingSubscribe = true) }
                .onCompletion { _state.value = internalState.copy(onLoadingSubscribe = false) }
                .catch {
                    _state.value = internalState.copy(onError = true, onSuccessSubscribe = false)
                }
                .collect {
                    _state.value = internalState.copy(onSuccessSubscribe = true, onError = false)
                }
        }
    }

    fun unsubscribeOnTopic() {
        viewModelScope.launch {
            subscribeUseCase.unsubscribeOnTopic(TOPIC)
                .flowOn(Dispatchers.IO)
                .onStart { _state.value = internalState.copy(onLoadingSubscribe = true) }
                .onCompletion { _state.value = internalState.copy(onLoadingSubscribe = false) }
                .catch {
                    _state.value = internalState.copy(onError = true, onSuccessUnsubscribe = false)
                }
                .collect {
                    _state.value = internalState.copy(onSuccessUnsubscribe = true, onError = false)
                }
        }
    }
}