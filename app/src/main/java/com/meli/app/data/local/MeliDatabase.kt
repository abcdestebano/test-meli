package com.meli.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.meli.app.model.SearchHistory

@Database(entities = [SearchHistory::class], version = 1)
abstract class MeliDatabase : RoomDatabase() {
    abstract fun searchDao(): SearchHistoryDao
}