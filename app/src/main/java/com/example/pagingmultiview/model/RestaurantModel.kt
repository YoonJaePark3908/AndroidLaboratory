package com.example.pagingmultiview.model

data class RestaurantModel(
    var comMsgHeader: ComMsgHeader = ComMsgHeader(),
    var msgHeader: MsgHeader = MsgHeader(),
    var data: List<Data> = listOf()
) {
    data class Data(
        var idx: String = "",
        var name: String = "",
        var contents1: String = "",
        var topMenu: String = "",
    )
}