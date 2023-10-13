package com.example.pagingmultiview

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pagingmultiview.model.MainPagingModel
import com.example.pagingmultiview.model.RestaurantModel
import com.example.pagingmultiview.model.TouristModel
import com.example.pagingmultiview.model.test.restaurantTestDataList
import com.example.pagingmultiview.model.test.touristTestDataList
import java.lang.Integer.min
import java.util.LinkedList

class MainPagingSource: PagingSource<Int, MainPagingModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MainPagingModel> =
        try {
            val next = params.key ?: 0

            val touristResponse = touristTestDataList[next] //기준
            val restaurantResponse =
                if (next <= restaurantTestDataList.size - 1) {
                    restaurantTestDataList[next]
                } else {
                    listOf()
                }
            val nextKey = if (touristTestDataList.size - 1 <= next) null else next + 1
            val pagingModelList = mappingPagingModel(touristResponse, restaurantResponse)
            Log.d("MainPagingSource", "idx = $next")
            Log.d("MainPagingSource", "pagingModelList = $pagingModelList")
            LoadResult.Page(
                data = pagingModelList,
                prevKey = if (next == 0) null else next - 1,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    override fun getRefreshKey(state: PagingState<Int, MainPagingModel>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }

    private fun mappingPagingModel(
        touristList: List<TouristModel>,
        restaurantList: List<RestaurantModel>
    ): List<MainPagingModel> {
        val result = LinkedList<MainPagingModel>()
        for (index in 0 until 10) { //10개 임의로 고정
            if (restaurantList.isNotEmpty()) {
                result.add(
                    MainPagingModel(
                        id = restaurantList[index].idx,
                        type = MainPagingModel.Type.Restaurant,
                        restaurant = restaurantList[index]
                    )
                )
            }
            if (touristList.isNotEmpty()) {
                result.add(
                    MainPagingModel(
                        id = touristList[index].id,
                        type = MainPagingModel.Type.Tourist,
                        tourist = touristList[index]
                    )
                )
            }
        }
        return result
    }
}