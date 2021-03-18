package com.meli.app.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meli.app.data.local.SearchRepository
import com.meli.app.model.SearchHistory
import com.meli.app.utils.MeliResult
import kotlinx.coroutines.launch

class SearchViewModel(private val searchRepository: SearchRepository) : ViewModel() {

    private val _searchHistory: MutableLiveData<MeliResult<List<SearchHistory>>> = MutableLiveData()
    val searchHistory: LiveData<MeliResult<List<SearchHistory>>> = _searchHistory

    fun getAllSearchHistory() {
        viewModelScope.launch {
            _searchHistory.value = searchRepository.getAllSearchHistory()
        }
    }

    fun saveSearch(searchText: String) {
        viewModelScope.launch {
            searchRepository.saveSearch(searchText)
        }
    }

}