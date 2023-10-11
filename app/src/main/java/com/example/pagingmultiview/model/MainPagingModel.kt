package com.example.pagingmultiview.model

data class MainPagingModel(
    var id: String = "",
    var type: Type = Type.Restaurant,
    var daejeonRestaurant: RestaurantModel.Data = RestaurantModel.Data(),
    var daejeonTourist: TouristModel.Data = TouristModel.Data()
) {
    enum class Type {
        Restaurant,
        Tourist
    }
}