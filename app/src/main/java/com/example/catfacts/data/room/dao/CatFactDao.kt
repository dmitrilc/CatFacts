package com.example.catfacts.data.room.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.example.catfacts.data.model.CatFact
import kotlinx.coroutines.flow.Flow

@Dao
interface CatFactDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(catFacts: List<CatFact>)

    @Query("DELETE FROM cat_fact")
    suspend fun deleteAll(): Int

    @Query("SELECT * FROM cat_fact")
    fun pagingSource(): PagingSource<Int, CatFact>

    @Query("SELECT COUNT(id) FROM cat_fact")
    fun count(): Flow<Int>

}