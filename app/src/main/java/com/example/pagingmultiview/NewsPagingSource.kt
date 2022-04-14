package com.example.pagingmultiview

import androidx.paging.PagingSource
import androidx.paging.PagingState

class NewsPagingSource: PagingSource<Int, MainPagingModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MainPagingModel> {
        return try {
            val next = params.key?: 0
            LoadResult.Page(
                data = listOf(MainPagingModel()),
                prevKey = if (next == 0) null else next - 1,
                nextKey = next + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MainPagingModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}