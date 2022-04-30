package com.example.catfacts.ui.viewmodel

import androidx.lifecycle.*
import androidx.paging.*
import com.example.catfacts.data.repository.CatFactRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
@HiltViewModel
class CatFactsViewModel @ExperimentalPagingApi @Inject constructor (
    private val catFactRepo: CatFactRepo
) : ViewModel() {

    private val _stats = MutableStateFlow(Stats(0,0,0))
    val statsFlow: StateFlow<Stats> = _stats

    init {
        viewModelScope.launch {
            catFactRepo.getStats().collect {
                _stats.emit(it)
            }
        }
    }

    @ExperimentalPagingApi
    val pagerWithRemoteMediator = catFactRepo.getCatFactPagerWithRemoteMediator()

}

data class Stats(
    val lastLoadedPage: Int,
    val lastPage: Int,
    val factsCount: Int
)