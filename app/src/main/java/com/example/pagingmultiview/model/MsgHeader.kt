package com.example.pagingmultiview.model

import com.google.gson.annotations.SerializedName

data class MsgHeader(
    @SerializedName("pageNo")
    var pageNo: Int = 0,
    @SerializedName("totalPage")
    var totalPage: Int = 0,
    @SerializedName("numOfRows")
    var numOfRows: Int = 0,
    @SerializedName("totalCount")
    var totalCount: Int = 0
)