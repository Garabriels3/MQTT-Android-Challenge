package com.br.mqttproject.ui.publish

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.br.domain.usecase.publish.PublishUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.lang.Exception

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class PublishViewsModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: PublishViewModel

    private var publishUseCase: PublishUseCase = mock()

    private var stateObserver: Observer<PublishStates> = mock()

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `publish should send message when success`() = runBlocking {
        // Given
        createViewModel()
        whenever(publishUseCase.publishMessage("gabrixx/feeds/test", "test"))
            .thenReturn(flow { emit(Unit) })

        // When
        viewModel.state.asLiveData().observeForever(stateObserver)
        viewModel.publish("test")

        // Then
        inOrder(stateObserver) {
            verify(stateObserver).onChanged(
                PublishStates(onLoadingPublish = false)
            )
            verify(stateObserver).onChanged(
                PublishStates(onLoadingPublish = true)
            )
            verify(stateObserver).onChanged(
                PublishStates(
                    onSuccessPublish = "Data published successfully!",
                    onLoadingPublish = false
                )
            )
        }
    }

    private fun createViewModel() {
        viewModel = PublishViewModel(publishUseCase)
    }
}