package com.example.pagingmultiview

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pagingmultiview.model.MainPagingModel
import com.example.pagingmultiview.model.RestaurantModel
import com.example.pagingmultiview.model.TouristModel
import java.lang.Integer.min
import java.util.LinkedList

class MainPagingSource : PagingSource<Int, MainPagingModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MainPagingModel> =
        try {
            val next = params.key ?: 1

            //TODO mock data 추가
//            val touristResponse =
//                retrofitClient.getService().requestDaejeonTouristList(touristHashMap)
//            val restaurantResponse =
//                retrofitClient.getService().requestDaejeonRestaurantList(restaurantHasMap)

//            val nextKey = if (touristResponse.body()!!.data.isEmpty() && restaurantResponse.body()!!.data.isEmpty()) null else next + 1
            //TODO nextKey
            val nextKey = if (true) null else next + 1
            if (true) {
                val pagingModelList = mappingPagingModel(listOf(), listOf())
                LoadResult.Page(
                    data = pagingModelList,
                    prevKey = if (next == 0) null else next - 1,
                    nextKey = nextKey
                )
            } else {
                LoadResult.Error(
                    Exception("error")
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

    private fun mappingPagingModel(
        touristList: List<TouristModel.Data>,
        restaurantList: List<RestaurantModel.Data>
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