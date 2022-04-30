package com.example.catfacts.hilt.service

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MoshiModule {
    companion object {
        @Provides
        @Singleton
        fun providesMoshi(): Moshi {
            return Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()
        }
    }
}