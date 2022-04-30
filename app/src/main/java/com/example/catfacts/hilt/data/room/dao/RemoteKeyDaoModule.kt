package com.example.catfacts.hilt.data.room.dao

import com.example.catfacts.data.room.dao.RemoteKeyDao
import com.example.catfacts.data.room.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteKeyDaoModule {

    @Provides
    @Singleton
    fun provideRemoteKeyDao(db: AppDatabase): RemoteKeyDao {
        return db.remoteKeyDao()
    }
}