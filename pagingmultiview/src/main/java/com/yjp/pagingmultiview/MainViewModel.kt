package com.yjp.pagingmultiview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.yjp.pagingmultiview.model.MainPagingModel
import kotlinx.coroutines.flow.Flow

class MainViewModel: ViewModel() {
    fun getPagingList(): Flow<PagingData<MainPagingModel>> {
        return Pager(PagingConfig(pageSize = 10)) {
            MainPagingSource()
        }.flow.cachedIn(viewModelScope)
    }
}