package com.br.mqttproject.ui.receive.adapter

import androidx.recyclerview.widget.RecyclerView
import com.br.domain.entity.Champion
import com.br.mqttproject.databinding.ItemCardChampionBinding

class ReceiveViewHolder(private val viewBinding: ItemCardChampionBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(item: Champion) {
        viewBinding.tvChampionName.text = item.squadName
        viewBinding.tvTitleYear.text = item.titleYear
    }
}