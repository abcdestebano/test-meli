package com.meli.app.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meli.app.databinding.ItemSearchHistoryBinding
import com.meli.app.model.SearchHistory

class SearchHistoryAdapter(
    private val onItemClickSearchHistory: ((String) -> Unit)
) : RecyclerView.Adapter<SearchHistoryViewHolder>() {

    var listSearchHistory: List<SearchHistory> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = ItemSearchHistoryBinding.inflate(layoutInflater, parent, false)
        return SearchHistoryViewHolder(itemView)
    }

    override fun getItemCount(): Int = listSearchHistory.size

    override fun onBindViewHolder(holder: SearchHistoryViewHolder, position: Int) {
        val item = listSearchHistory[position]
        holder.bindViewHolder(item.searchText, onItemClickSearchHistory)
    }

}