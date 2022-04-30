package com.example.catfacts.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "cat_fact",
    indices = [Index(
        value = ["fact"],
        unique = true
    )]
)
data class CatFact(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val fact: String,
    val length: Int
)