package com.meli.app.ui.search

import androidx.recyclerview.widget.RecyclerView
import com.meli.app.databinding.ItemSearchHistoryBinding

class SearchHistoryViewHolder(
    private val binding: ItemSearchHistoryBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bindViewHolder(searchText: String, onItemClickSearchHistory: (String) -> Unit) {
        binding.apply {
            txvItemSearchHistory.text = searchText
            root.setOnClickListener { onItemClickSearchHistory.invoke(searchText) }
        }
    }

}