package com.example.pagingmultiview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pagingmultiview.model.MainPagingModel
import kotlinx.coroutines.flow.Flow

class MainViewModel: ViewModel() {
    fun getPagingList(): Flow<PagingData<MainPagingModel>> {
        return Pager(PagingConfig(pageSize = 10)) {
            MainPagingSource()
        }.flow.cachedIn(viewModelScope)
    }
}