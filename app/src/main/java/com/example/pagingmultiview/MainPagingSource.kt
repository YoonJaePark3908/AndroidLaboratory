package com.example.pagingmultiview

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pagingmultiview.model.ReqTestModel
import com.example.pagingmultiview.model.RespTestModel
import com.example.pagingmultiview.network.RetrofitClient
import java.io.IOException
import java.io.InputStream

class MainPagingSource(
    private val retrofitClient: RetrofitClient
): PagingSource<Int, RespTestModel.MsgBody>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RespTestModel.MsgBody> {
        return try {
            val next = params.key?: 1
            val response = retrofitClient.getService().requestTestAPI(ReqTestModel(pageNo = next))
            if (response.isSuccessful) {
                LoadResult.Page(
                    data = response.body()!!.msgBodyList,
                    prevKey = if (next == 0) null else next - 1,
                    nextKey = next + 1
                )
            } else {
                LoadResult.Error(Exception("${response.errorBody()}, ${response.errorBody().toString()}"))
            }

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, RespTestModel.MsgBody>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}