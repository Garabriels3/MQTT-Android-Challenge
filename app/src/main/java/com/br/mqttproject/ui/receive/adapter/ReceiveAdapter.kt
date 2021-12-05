package com.br.mqttproject.ui.receive.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.br.domain.entity.Champion
import com.br.mqttproject.databinding.ItemCardChampionBinding

class ReceiveAdapter : ListAdapter<Champion, ReceiveViewHolder>(ReceiveDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceiveViewHolder {
        return ReceiveViewHolder(
            ItemCardChampionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ReceiveViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}