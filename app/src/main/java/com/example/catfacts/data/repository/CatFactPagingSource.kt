package com.example.catfacts.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.catfacts.data.model.CatFact
import com.example.catfacts.service.WebService
import okio.IOException
import retrofit2.HttpException

private const val CAT_FACTS_STARTING_PAGE_INDEX = 1

class CatFactPagingSource(
    private val webService: WebService,
    private val pageNumberUpdater: (page: Int)->Unit
    ) : PagingSource<Int, CatFact>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CatFact> {
        try {
            // Start refresh at page 1 if undefined.
            val position = params.key ?: CAT_FACTS_STARTING_PAGE_INDEX
            pageNumberUpdater(position)

            val response = webService.getCatFactPage(position)
            val catFacts = response.data

            val prevKey = if (position == CAT_FACTS_STARTING_PAGE_INDEX) {
                null //if current position is the same as the starting position
            } else {
                position - 1
            }

            val nextKey = if (catFacts.isEmpty()){
                null //if there is no more data to load in the current direction
            } else {
                position + 1
            }

            return LoadResult.Page(
                data = catFacts,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CatFact>): Int? {
        /*
        This will always start loading from the beginning.
        Same effect as return AT_FACTS_STARTING_PAGE_INDEX.
        */
        return null
        //If you want the list to resume at page 5, return 5, etc..
    }

}