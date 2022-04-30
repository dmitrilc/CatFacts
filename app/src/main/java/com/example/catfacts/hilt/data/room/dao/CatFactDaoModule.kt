package com.example.catfacts.hilt.data.room.dao

import com.example.catfacts.data.room.db.AppDatabase
import com.example.catfacts.data.room.dao.CatFactDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CatFactDaoModule {

    @Provides
    @Singleton
    fun provideCatFactDao(db: AppDatabase): CatFactDao {
        return db.catFactDao()
    }
}