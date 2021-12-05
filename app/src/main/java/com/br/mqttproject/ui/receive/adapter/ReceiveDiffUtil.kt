package com.br.mqttproject.ui.receive.adapter

import androidx.recyclerview.widget.DiffUtil
import com.br.domain.entity.Champion

class ReceiveDiffUtil : DiffUtil.ItemCallback<Champion>() {
    override fun areItemsTheSame(
        oldItem: Champion,
        newItem: Champion
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: Champion,
        newItem: Champion
    ): Boolean {
        return oldItem.squadName == newItem.squadName
    }
}