package com.example.pagingmultiview.network

import com.example.pagingmultiview.model.RespDaejeonRestaurantModel
import com.example.pagingmultiview.model.RespDaejeonTouristModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface RetrofitService {
    /**
     * 대전 문화 관광지 목록 조회
     */
    @GET("/6300000/tourDataService/tourDataListJson")
    suspend fun requestDaejeonTouristList(@QueryMap params: HashMap<String, String>): Response<RespDaejeonTouristModel>
    /**
     * 대전 음식점 목록 조회
     */
    @GET("/6300000/tourFoodDataService/tourFoodDataListJson")
    suspend fun requestDaejeonRestaurantList(@QueryMap params: HashMap<String, String>): Response<RespDaejeonRestaurantModel>
}