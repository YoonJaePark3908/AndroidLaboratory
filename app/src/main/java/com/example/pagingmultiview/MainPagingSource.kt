package com.example.pagingmultiview

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pagingmultiview.model.MainPagingModel
import com.example.pagingmultiview.model.RespDaejeonRestaurantModel
import com.example.pagingmultiview.model.RespDaejeonTouristModel
import com.example.pagingmultiview.network.RetrofitClient

class MainPagingSource(
    private val retrofitClient: RetrofitClient
): PagingSource<Int, RespDaejeonTouristModel.MsgBody>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RespDaejeonTouristModel.MsgBody> {
        return try {
            val next  = params.key?: 1
            val touristHashMap = getTouristHashMap(next)
            val restaurantHasMap = getRestaurantHashMap(next)

            val touristResponse = retrofitClient.getService().requestDaejeonTouristList(touristHashMap)
            val restaurantResponse = retrofitClient.getService().requestDaejeonRestaurantList(restaurantHasMap)

            val nextKey = if (touristResponse.body()!!.msgBody.isEmpty() && restaurantResponse.body()!!.msgBody.isEmpty()) null else next + 1
            if (touristResponse.isSuccessful && restaurantResponse.isSuccessful) {

                LoadResult.Page(
                    data = touristResponse.body()!!.msgBody,
                    prevKey = if (next == 0) null else next - 1,
                    nextKey = nextKey
                )
            } else {
                LoadResult.Error(Exception("${touristResponse.errorBody()}, ${touristResponse.errorBody().toString()}"))
            }

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, RespDaejeonTouristModel.MsgBody>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    private fun getTouristHashMap(pageNo: Int): HashMap<String, String> {
        val hashMap = HashMap<String, String>()
        hashMap["serviceKey"] = "THnjzgvbJb4e6Do4H22ehdKY0i1MSGfgzbEeOddm5QPipubruRWCa85lj1dS95Ji/BcaIiPUOPhfr+ziyLFgvw=="
        hashMap["pageNo"] = pageNo.toString()
        hashMap["numOfRows"] = "10"
        hashMap["dcode"] = "C0101"
        hashMap["searchCondition"] = ""
        hashMap["searchKeyword"] = ""
        return hashMap
    }

    private fun getRestaurantHashMap(pageNo: Int): HashMap<String, String> {
        val hashMap = HashMap<String, String>()
        hashMap["serviceKey"] = "THnjzgvbJb4e6Do4H22ehdKY0i1MSGfgzbEeOddm5QPipubruRWCa85lj1dS95Ji/BcaIiPUOPhfr+ziyLFgvw=="
        hashMap["pageNo"] = pageNo.toString()
        hashMap["numOfRows"] = "10"
        hashMap["dcode"] = "C0301"
        hashMap["dgu"] = "C0601"
        hashMap["searchCondition"] = "1"
        hashMap["searchKeyword"] = ""
        return hashMap
    }

    private fun mappingPagingModel(
        touristList: List<RespDaejeonTouristModel.MsgBody>,
        restaurantList: List<RespDaejeonRestaurantModel.MsgBody>
    ): List<MainPagingModel> {
        when {
            touristList.size == restaurantList.size -> {
                
            }
            touristList.size > restaurantList.size -> {

            }
            touristList.size < restaurantList.size -> {

            }
        }
        return listOf()
    }
}