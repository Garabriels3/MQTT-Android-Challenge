package com.br.mqttproject.ui.receive

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import com.br.mqttproject.databinding.FragmentSubscribeBinding
import com.br.mqttproject.ui.home.HomeStates
import com.br.mqttproject.ui.receive.adapter.ReceiveAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val SUBSCRIBE_SUCCESS = "Subscribe with success"
private const val UNSUBSCRIBE_SUCCESS = "Unsubscribe with success"
private const val NEW_DATA_ARRIVED = "New data arrived!"
private const val ERROR = "Ops, there's something wrong"

class SubscribeFragment : Fragment() {

    private val viewModel: SubscribeViewModel by viewModel()
    private val binding: FragmentSubscribeBinding by lazy {
        FragmentSubscribeBinding.inflate(layoutInflater)
    }
    private val adapter: ReceiveAdapter by lazy {
        ReceiveAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setObservables()
        setButtonListeners()
        viewModel.onMessageArrived()
    }

    private fun setupAdapter() {
        binding.rvChampionList.adapter = adapter
    }

    private fun setButtonListeners() {
        binding.btnSubscribe.setOnClickListener {
            viewModel.subscribeOnTopic()
        }

        binding.btnUnsubscribe.setOnClickListener {
            viewModel.unsubscribeOnTopic()
        }
    }

    private fun setObservables() {
        viewModel.state.asLiveData().observeForever {
            with(it) {
                setListOnSuccessState()
                setOnSubscribeSuccess()
                setOUnsubscribeSuccess()
                setErrorState()
                setLoadingState()
            }
        }
    }

    private fun SubscribeStates.setListOnSuccessState() {
        onSuccessMessageArrived?.takeIf { !it.isNullOrEmpty() }?.let {
            adapter.submitList(it)
            Toast.makeText(
                context,
                NEW_DATA_ARRIVED,
                Toast.LENGTH_SHORT
            ).show()
            binding.pbLoading.visibility = View.GONE
        }
    }

    private fun SubscribeStates.setOnSubscribeSuccess() {
        onSuccessSubscribe.takeIf { it }?.let {
            Toast.makeText(
                context,
                SUBSCRIBE_SUCCESS,
                Toast.LENGTH_SHORT
            ).show()
            binding.pbLoading.visibility = View.GONE
        }
    }

    private fun SubscribeStates.setOUnsubscribeSuccess() {
        onSuccessUnsubscribe.takeIf { it }?.let {
            Toast.makeText(
                context,
                UNSUBSCRIBE_SUCCESS,
                Toast.LENGTH_SHORT
            ).show()
            binding.pbLoading.visibility = View.GONE
        }
    }

    private fun SubscribeStates.setErrorState() {
        onError.takeIf { it }?.let {
            Toast.makeText(
                context,
                ERROR,
                Toast.LENGTH_SHORT
            ).show()
            binding.pbLoading.visibility = View.GONE
        }
    }

    private fun SubscribeStates.setLoadingState() {
        onLoadingSubscribe.takeIf { it }?.run {
            binding.pbLoading.visibility = View.VISIBLE
        }
    }
}