package com.example.pagingmultiview.model

import com.google.gson.annotations.SerializedName

data class MainPagingModel(
    var id: String = "",
    var type: Type = Type.Restaurant,
    var daejeonRestaurant: RespDaejeonRestaurantModel.MsgBody = RespDaejeonRestaurantModel.MsgBody(),
    var daejeonTourist: RespDaejeonTouristModel.MsgBody = RespDaejeonTouristModel.MsgBody()
) {
    enum class Type {
        Restaurant,
        Tourist
    }
}