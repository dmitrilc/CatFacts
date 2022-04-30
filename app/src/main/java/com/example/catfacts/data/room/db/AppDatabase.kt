package com.example.catfacts.data.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.catfacts.data.model.CatFact
import com.example.catfacts.data.model.RemoteKey
import com.example.catfacts.data.room.dao.CatFactDao
import com.example.catfacts.data.room.dao.RemoteKeyDao

@Database(
    entities = [CatFact::class, RemoteKey::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun catFactDao(): CatFactDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}