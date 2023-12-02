package com.example.pagingmultiview

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pagingmultiview.model.MainPagingModel
import com.example.pagingmultiview.model.mock.restaurantTestDataList
import com.example.pagingmultiview.model.mock.touristTestDataList
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
        touristList: List<MainPagingModel.TouristModel>,
        restaurantList: List<MainPagingModel.RestaurantModel>
    ): List<MainPagingModel> {
        val result = LinkedList<MainPagingModel>()
        for (index in 0 until 10) { //10개 임의로 고정
            if (restaurantList.isNotEmpty()) {
                result.add(
                    restaurantList[index].copy(
                        name = "${restaurantList[index].idx}번째 음식점 이름",
                        contents1 = "${restaurantList[index].idx}번째 음식점 소개 내용",
                        topMenu = "${restaurantList[index].idx}번째 음식점 인기 메뉴"
                    )
                )
            }
            if (touristList.isNotEmpty()) {
                result.add(
                    touristList[index].copy(
                        addr1 = "${touristList[index].id}번째 주소명",
                        name = "${touristList[index].id}번째 여행지 이름"
                    )
                )
            }
        }
        return result
    }
}