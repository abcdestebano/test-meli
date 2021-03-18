package com.meli.app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.meli.app.model.SearchHistory

@Dao
interface SearchHistoryDao {

    @Query("SELECT * FROM search_history")
    fun getSearchHistory(): List<SearchHistory>

    @Insert
    fun saveSearch(search: SearchHistory)

}