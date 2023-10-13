package com.example.pagingmultiview.model

data class MainPagingModel(
    var id: String = "",
    var type: Type = Type.Restaurant,
    var restaurant: RestaurantModel = RestaurantModel(),
    var tourist: TouristModel= TouristModel()
) {
    enum class Type {
        Restaurant,
        Tourist
    }
}