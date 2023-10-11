package com.example.pagingmultiview.model

data class MsgHeader(
    var pageNo: Int = 0,
    var totalPage: Int = 0,
    var numOfRows: Int = 0,
    var totalCount: Int = 0
)