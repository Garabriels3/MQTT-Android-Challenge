package com.br.mqttproject.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.domain.usecase.connect.ConnectMqttUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel(private val connectMqttUseCase: ConnectMqttUseCase) : ViewModel() {

    val state: StateFlow<HomeStates> get() = _state

    private val _state: MutableStateFlow<HomeStates> = MutableStateFlow(HomeStates())

    private val internalState = HomeStates()

    fun connect() {
        viewModelScope.launch {
            connectMqttUseCase.connectMqtt()
                .flowOn(Dispatchers.IO)
                .onStart {
                    _state.value =
                        internalState.copy(onLoading = true)
                }
                .onCompletion {
                    _state.value =
                        internalState.copy(onLoading = false)
                }
                .catch {
                    _state.value =
                        internalState.copy(onError = true, onSuccess = false)
                }
                .collect {
                    _state.value =
                        internalState.copy(onError = false, onSuccess = true)
                }
        }
    }
}