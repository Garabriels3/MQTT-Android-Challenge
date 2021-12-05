package com.br.mqttproject.ui.publish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.br.mqttproject.databinding.FragmentPublishBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val WORLD_CUP_CHAMPIONS =
    "[{ \"country\": \"France\", \"year\": 2018 },{ \"country\": \"Germany\", \"year\": 2014 },{ \"country\": \"Spain\", \"year\": 2010 },{ \"country\": \"Italy\", \"year\": 2006 },{ \"country\": \"Brazil\", \"year\": 2002 },{ \"country\": \"France\", \"year\": 1998 },{ \"country\": \"Brazil\", \"year\": 1994 },{ \"country\": \"West Germany\", \"year\": 1990 },{ \"country\": \"Argentina\", \"year\": 1986 },{ \"country\": \"Italy\", \"year\": 1982 },{ \"country\": \"Argentina\", \"year\": 1978 },\n" +
            "{ \"country\": \"West Germany\", \"year\": 1974 },{ \"country\": \"Brazil\", \"year\": 1970 },{ \"country\": \"England\", \"year\": 1966 },{ \"country\": \"Brazil\", \"year\": 1962 },{ \"country\": \"Brazil\", \"year\": 1958 },{ \"country\": \"West Germany\", \"year\": 1954 },{ \"country\": \"Uruguay\", \"year\": 1950 },{ \"country\": \"Italy\", \"year\": 1938 },\n" +
            "{ \"country\": \"Italy\", \"year\": 1934 },{ \"country\": \"Uruguay\", \"year\": 1930 }]"

class PublishFragment : Fragment() {

    private val viewModel: PublishViewModel by viewModel()

    private val binding: FragmentPublishBinding by lazy {
        FragmentPublishBinding.inflate(layoutInflater)
    }

    private val navController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onObservablesStates()
        setButtonListeners()
    }

    private fun setButtonListeners() {
        with(binding) {
            tvPublish.setOnClickListener {
                viewModel.publish(etMsg.text.toString())
            }
            btnAutoFill.setOnClickListener {
                etMsg.setText(WORLD_CUP_CHAMPIONS)
            }
        }
    }

    private fun onObservablesStates() {
        viewModel.state.asLiveData().observe(viewLifecycleOwner) {
            with(it) {
                setPublishErrorState()
                setPublishSuccessState()
                setPublishLoadingState()
            }
        }
    }

    private fun PublishStates.setPublishErrorState() {
        onErrorPublish.takeIf { it.isNotEmpty() }?.let {
            Toast.makeText(
                context,
                it,
                Toast.LENGTH_SHORT
            ).show()
            binding.pbLoading.visibility = View.GONE
        }
    }

    private fun PublishStates.setPublishSuccessState() {
        onSuccessPublish.takeIf { it.isNotEmpty() }?.let {
            Toast.makeText(
                context,
                it,
                Toast.LENGTH_SHORT
            ).show()
            binding.pbLoading.visibility = View.GONE
        }
    }

    private fun PublishStates.setPublishLoadingState() {
        onLoadingPublish.takeIf { it }?.let {
            binding.pbLoading.visibility = View.VISIBLE
        }
    }
}