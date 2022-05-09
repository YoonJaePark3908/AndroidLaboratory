package com.example.pagingmultiview.network

import com.example.pagingmultiview.model.ReqTestModel
import com.example.pagingmultiview.model.RespTestModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET

interface RetrofitService {
    /**
     * Test API
     */
    @GET("/6300000/tourDataService/tourDataListJson")
    suspend fun requestTestAPI(@Body testMode: ReqTestModel): Response<RespTestModel>
}