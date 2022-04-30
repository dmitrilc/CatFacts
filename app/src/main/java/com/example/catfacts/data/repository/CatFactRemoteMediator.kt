package com.example.catfacts.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.catfacts.data.model.CatFact
import com.example.catfacts.data.model.RemoteKey
import com.example.catfacts.data.room.db.AppDatabase
import com.example.catfacts.data.room.dao.CatFactDao
import com.example.catfacts.data.room.dao.RemoteKeyDao
import com.example.catfacts.service.WebService
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

private const val TAG = "REMOTE_MEDIATOR"
private const val CAT_FACTS_STARTING_PAGE_INDEX = 1

@ExperimentalPagingApi
class CatFactRemoteMediator @Inject constructor(
    private val webService: WebService,
    val catFactDao: CatFactDao,
    private val remoteKeyDao: RemoteKeyDao,
    private val database: AppDatabase
) : RemoteMediator<Int, CatFact>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CatFact>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    catFactDao.deleteAll()
                    remoteKeyDao.deleteAll()
                    CAT_FACTS_STARTING_PAGE_INDEX
                }
                LoadType.PREPEND -> return MediatorResult.Success(true)
                LoadType.APPEND -> {
                    val remoteKey = remoteKeyDao.get()!!

                    if (remoteKey.currentPage == remoteKey.lastPage){
                        return MediatorResult.Success(true)
                    }

                    remoteKey.currentPage.plus(1)
                }
            }

            val response = webService.getCatFactPage(loadKey)

            database.withTransaction {
                remoteKeyDao.insertOrReplace(
                    RemoteKey(
                        currentPage = response.current_page,
                        lastPage = response.last_page
                    ))

                catFactDao.insertAll(response.data)
            }

            MediatorResult.Success(false)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {
        val remoteKey = remoteKeyDao.get()
        return if (remoteKey == null){
            InitializeAction.LAUNCH_INITIAL_REFRESH
        } else {
            InitializeAction.SKIP_INITIAL_REFRESH
        }
    }
}