package com.example.catfacts.hilt.service

import com.example.catfacts.service.WebService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class WebServiceModule {

    companion object {
        private const val BASE_URL = "https://catfact.ninja"

        @Provides
        @Singleton
        fun providesWebservice(moshi: Moshi): WebService {
            return Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(BASE_URL)
                .build()
                .create(WebService::class.java)
        }
    }

}