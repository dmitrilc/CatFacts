package com.example.catfacts.service

import com.example.catfacts.data.model.CatFact

data class CatFactPageJson(
    val current_page: Int,
    val last_page: Int,
    val data: List<CatFact>
)
