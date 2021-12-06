package com.br.mqttproject.ui.receive

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.br.domain.entity.Champion
import com.br.domain.usecase.messagearrived.SubscribeUseCase
import com.br.mqttproject.testrules.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class SubscribeViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private lateinit var viewModel: SubscribeViewModel

    private val subscribeUseCase: SubscribeUseCase = mock()
    private var stateObserver: Observer<SubscribeStates> = mock()

    @Test
    fun `onMessageArrived should return a champion list when success`() {
        // Given
        createViewModel()
        whenever(subscribeUseCase.messageArrived())
            .thenReturn(flow { emit(listOf(Champion(squadName = "Germany", titleYear = "2014"))) })

        // When
        viewModel.state.asLiveData().observeForever(stateObserver)
        viewModel.onMessageArrived()

        // Then
        inOrder(stateObserver) {
            verify(stateObserver).onChanged(
                SubscribeStates(onLoadingMessageArriving = false)
            )
            verify(stateObserver).onChanged(
                SubscribeStates(onLoadingMessageArriving = true)
            )
            verify(stateObserver).onChanged(
                SubscribeStates(
                    onSuccessMessageArrived = listOf(
                        Champion(
                            squadName = "Germany",
                            titleYear = "2014"
                        )
                    ),
                    onLoadingMessageArriving = false
                )
            )
        }
    }

    @Test
    fun `onMessageArrived should set onError true when error`() {
        // Given
        createViewModel()
        whenever(subscribeUseCase.messageArrived())
            .thenReturn(flow { throw Exception() })

        // When
        viewModel.state.asLiveData().observeForever(stateObserver)
        viewModel.onMessageArrived()

        // Then
        inOrder(stateObserver) {
            verify(stateObserver).onChanged(
                SubscribeStates(onLoadingMessageArriving = false)
            )
            verify(stateObserver).onChanged(
                SubscribeStates(onLoadingMessageArriving = true)
            )
            verify(stateObserver).onChanged(
                SubscribeStates(
                    onError = true,
                    onLoadingMessageArriving = false
                )
            )
        }
    }

    private fun createViewModel() {
        viewModel = SubscribeViewModel(subscribeUseCase, coroutineRule.dispatcher)
    }
}