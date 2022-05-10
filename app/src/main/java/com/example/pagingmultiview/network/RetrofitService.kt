package com.example.pagingmultiview.network

import com.example.pagingmultiview.model.ReqTestModel
import com.example.pagingmultiview.model.RespTestModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface RetrofitService {
    /**
     * Test API
     */
    @GET("/6300000/tourDataService/tourDataListJson")
    suspend fun requestTestAPI(@QueryMap params: HashMap<String, String>): Response<RespTestModel>
}