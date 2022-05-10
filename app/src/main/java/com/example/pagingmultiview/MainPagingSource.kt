package com.example.pagingmultiview

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pagingmultiview.model.RespTestModel
import com.example.pagingmultiview.network.RetrofitClient

class MainPagingSource(
    private val retrofitClient: RetrofitClient
): PagingSource<Int, RespTestModel.MsgBody>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RespTestModel.MsgBody> {
        return try {
            val next = params.key?: 1
            val hashMap = HashMap<String, String>()
            hashMap["serviceKey"] = "THnjzgvbJb4e6Do4H22ehdKY0i1MSGfgzbEeOddm5QPipubruRWCa85lj1dS95Ji/BcaIiPUOPhfr+ziyLFgvw=="
            hashMap["pageNo"] = next.toString()
            hashMap["numOfRows"] = "10"
            hashMap["dcode"] = "C0101"
            hashMap["searchCondition"] = ""
            hashMap["searchKeyword"] = ""

            val response = retrofitClient.getService().requestTestAPI(hashMap)
            if (response.isSuccessful) {
                LoadResult.Page(
                    data = response.body()!!.msgBody,
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