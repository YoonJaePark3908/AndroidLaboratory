package com.example.pagingmultiview.model

data class TouristModel(
    var comMsgHeader: ComMsgHeader = ComMsgHeader(),
    var msgHeader: MsgHeader = MsgHeader(),
    var data: List<Data> = listOf()
) {

    data class Data(
        var id: String = "",
        var addr1: String = "",
        var name: String = "",
    )
}