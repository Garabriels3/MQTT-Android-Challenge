package com.br.mqttproject.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.br.mqttproject.R
import com.br.mqttproject.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val MQTT_CONNECTION_ERROR = "MQTT Connect ERROR"
private const val MQTT_CONNECTION_SUCCESS = "MQTT Connect Success"

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()

    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    private val navController by lazy {
        findNavController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onObservablesStates()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vSend.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_publishFragment)
        }

        binding.ivReceive.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_subscribeFragment)
        }

        binding.btnConnect.setOnClickListener {
            viewModel.connect()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    private fun onObservablesStates() {
        lifecycleScope.launchWhenCreated {
            viewModel.state.collect {
                with(it) {
                    setErrorState()
                    setSuccessState()
                    setLoadingState()
                }
            }
        }
    }

    private fun HomeStates.setErrorState() {
        onError.takeIf { it }?.run {
            Toast.makeText(
                context,
                MQTT_CONNECTION_ERROR,
                Toast.LENGTH_SHORT
            ).show()
            binding.pbLoading.visibility = View.GONE
        }
    }

    private fun HomeStates.setSuccessState() {
        onSuccess.takeIf { it }?.run {
            Toast.makeText(
                context,
                MQTT_CONNECTION_SUCCESS,
                Toast.LENGTH_SHORT
            ).show()
            binding.pbLoading.visibility = View.GONE
        }
    }

    private fun HomeStates.setLoadingState() {
        onLoading.takeIf { it }?.run {
            binding.pbLoading.visibility = View.VISIBLE
        }
    }
}