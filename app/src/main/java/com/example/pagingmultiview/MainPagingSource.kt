package com.example.pagingmultiview

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pagingmultiview.model.MainPagingModel
import com.example.pagingmultiview.model.RespDaejeonRestaurantModel
import com.example.pagingmultiview.model.RespDaejeonTouristModel
import com.example.pagingmultiview.network.RetrofitClient
import java.lang.Integer.min
import java.util.*
import kotlin.collections.HashMap

class MainPagingSource(
    private val retrofitClient: RetrofitClient
) : PagingSource<Int, MainPagingModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MainPagingModel> =
        try {
            val next = params.key ?: 1
            val touristHashMap = getTouristHashMap(next)
            val restaurantHasMap = getRestaurantHashMap(next)

            val touristResponse =
                retrofitClient.getService().requestDaejeonTouristList(touristHashMap)
            val restaurantResponse =
                retrofitClient.getService().requestDaejeonRestaurantList(restaurantHasMap)

            val nextKey =
                if (touristResponse.body()!!.msgBody.isEmpty() && restaurantResponse.body()!!.msgBody.isEmpty()) null else next + 1
            if (touristResponse.isSuccessful && restaurantResponse.isSuccessful) {
                val pagingModelList = mappingPagingModel(touristResponse.body()!!.msgBody, restaurantResponse.body()!!.msgBody)
                LoadResult.Page(
                    data = pagingModelList,
                    prevKey = if (next == 0) null else next - 1,
                    nextKey = nextKey
                )
            } else {
                LoadResult.Error(
                    Exception("${touristResponse.errorBody()}, ${touristResponse.errorBody().toString()}")
                )
            }

        } catch (e: Exception) {
            LoadResult.Error(e)
        }


    override fun getRefreshKey(state: PagingState<Int, MainPagingModel>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }


    private fun getTouristHashMap(pageNo: Int): HashMap<String, String> =
        HashMap<String, String>().apply {
            this["serviceKey"] = "THnjzgvbJb4e6Do4H22ehdKY0i1MSGfgzbEeOddm5QPipubruRWCa85lj1dS95Ji/BcaIiPUOPhfr+ziyLFgvw=="
            this["pageNo"] = pageNo.toString()
            this["numOfRows"] = "10"
            this["dcode"] = "C0101"
            this["searchCondition"] = ""
            this["searchKeyword"] = ""
        }
    

    private fun getRestaurantHashMap(pageNo: Int): HashMap<String, String> =
        HashMap<String, String>().apply {
            this["serviceKey"] =
                "THnjzgvbJb4e6Do4H22ehdKY0i1MSGfgzbEeOddm5QPipubruRWCa85lj1dS95Ji/BcaIiPUOPhfr+ziyLFgvw=="
            this["pageNo"] = pageNo.toString()
            this["numOfRows"] = "10"
            this["dcode"] = "C0301"
            this["dgu"] = "C0601"
            this["searchCondition"] = "1"
            this["searchKeyword"] = ""
        }


    private fun mappingPagingModel(
        touristList: List<RespDaejeonTouristModel.MsgBody>,
        restaurantList: List<RespDaejeonRestaurantModel.MsgBody>
    ): List<MainPagingModel> {
        val result = LinkedList<MainPagingModel>()
        val minSize = min(touristList.size, restaurantList.size)
        for (index in 0 until minSize) {
            result.add(
                MainPagingModel(
                    id = restaurantList[index].idx,
                    type = MainPagingModel.Type.Restaurant,
                    daejeonRestaurant = restaurantList[index]
                )
            )
            result.add(
                MainPagingModel(
                    id = touristList[index].id,
                    type = MainPagingModel.Type.Tourist,
                    daejeonTourist = touristList[index]
                )
            )
        }
        when {
            touristList.size > restaurantList.size -> {
                for (index in restaurantList.lastIndex until touristList.size) {
                    result.add(
                        MainPagingModel(
                            id = touristList[index].id,
                            type = MainPagingModel.Type.Tourist,
                            daejeonTourist = touristList[index]
                        )
                    )
                }
            }
            touristList.size < restaurantList.size -> {
                for (index in touristList.lastIndex until restaurantList.size) {
                    result.add(
                        MainPagingModel(
                            id = restaurantList[index].idx,
                            type = MainPagingModel.Type.Restaurant,
                            daejeonRestaurant = restaurantList[index]
                        )
                    )
                }
            }
        }
        return result
    }
}