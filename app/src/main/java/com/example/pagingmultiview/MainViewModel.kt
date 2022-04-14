package com.example.pagingmultiview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow

class MainViewModel: ViewModel() {
    fun getPagingList(): Flow<PagingData<MainPagingModel>> {
        return Pager(PagingConfig(pageSize = 10)) {
            NewsPagingSource()
        }.flow.cachedIn(viewModelScope)
    }
}