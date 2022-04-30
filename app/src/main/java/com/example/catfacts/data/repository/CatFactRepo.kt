package com.example.catfacts.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.catfacts.data.model.CatFact
import com.example.catfacts.data.room.dao.CatFactDao
import com.example.catfacts.data.room.dao.RemoteKeyDao
import com.example.catfacts.ui.viewmodel.Stats
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@ExperimentalPagingApi
class CatFactRepo @Inject constructor (
    private val catFactDao: CatFactDao,
    private val remoteKeyDao: RemoteKeyDao,
    private val catFactRemoteMediator: CatFactRemoteMediator
) {

    fun getCatFactPagerWithRemoteMediator(): Flow<PagingData<CatFact>> =
        Pager(
            PagingConfig(1),
            remoteMediator = catFactRemoteMediator
        ) {
            catFactDao.pagingSource()
        }
            .flow

    fun getStats(): Flow<Stats>{
        return remoteKeyDao
            .getKeyFlow()
            .combine(
                catFactDao.count()
            ) { key, count ->
                Stats(
                    lastLoadedPage = key?.currentPage ?: 0,
                    lastPage = key?.lastPage ?: 0,
                    factsCount = count
                )
            }
    }

}

