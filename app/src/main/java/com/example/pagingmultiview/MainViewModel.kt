package com.example.pagingmultiview

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pagingmultiview.model.RespTestModel
import com.example.pagingmultiview.network.RetrofitClient
import kotlinx.coroutines.flow.Flow

class MainViewModel: ViewModel() {
    fun getPagingList(context: Context): Flow<PagingData<RespTestModel.MsgBody>> {
        return Pager(PagingConfig(pageSize = 10)) {
            MainPagingSource(RetrofitClient(context))
        }.flow.cachedIn(viewModelScope)
    }
}