package com.meli.app.data.local

import com.meli.app.model.SearchHistory
import com.meli.app.utils.MeliResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.Exception

class SearchRepository(private val searchHistoryDao: SearchHistoryDao) {

    private val dispatcher = Dispatchers.IO

    suspend fun getAllSearchHistory(): MeliResult<List<SearchHistory>> {
        return try {
            withContext(dispatcher) {
                val listSearchHistory = searchHistoryDao.getSearchHistory()
                MeliResult.Success(listSearchHistory)
            }
        } catch (exception: Exception) {
            MeliResult.Error(exception.toString())
        }
    }

    suspend fun saveSearch(query: String) {
        withContext(dispatcher) {
            val listSearchHistory = searchHistoryDao.getSearchHistory()
            val queryAlreadyExists = listSearchHistory.any { item ->
                item.searchText.toLowerCase(Locale.getDefault())
                    .contains(query.toLowerCase(Locale.getDefault()))
            }
            if (!queryAlreadyExists) {
                searchHistoryDao.saveSearch(SearchHistory(searchText = query))
            }
        }
    }

}