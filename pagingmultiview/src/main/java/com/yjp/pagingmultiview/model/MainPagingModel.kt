package com.yjp.pagingmultiview.model

sealed interface MainPagingModel {
    data class RestaurantModel(
        val idx: String = "",
        val name: String = "",
        val contents1: String = "",
        val topMenu: String = ""
    ): MainPagingModel

    data class TouristModel(
        val id: String = "",
        val addr1: String = "",
        val name: String = "",
    ): MainPagingModel
}
