package com.example.catfacts.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_key")
data class RemoteKey(
    @PrimaryKey val id: Int = 1,
    @ColumnInfo("current_page") val currentPage: Int,
    @ColumnInfo("last_page") val lastPage: Int
    )
