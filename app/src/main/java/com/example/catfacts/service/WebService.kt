package com.example.catfacts.service

import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    @GET("/facts")
    suspend fun getCatFactPage(@Query("page") page: Int): CatFactPageJson

}