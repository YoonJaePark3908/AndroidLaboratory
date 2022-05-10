package com.example.pagingmultiview.model

import com.google.gson.annotations.SerializedName

data class RespDaejeonRestaurantModel(
    @SerializedName("comMsgHeader")
    var comMsgHeader: ComMsgHeader = ComMsgHeader(),
    @SerializedName("msgHeader")
    var msgHeader: MsgHeader = MsgHeader(),
    @SerializedName("msgBody")
    var msgBody: List<MsgBody> = listOf()
) {
    data class MsgBody(
        @SerializedName("idx")
        var idx: String = "",
        @SerializedName("dCode")
        var dCode: String = "",
        @SerializedName("dCodeNm")
        var dCodeNm: String = "",
        @SerializedName("dGu")
        var dGu: String = "",
        @SerializedName("dGuNm")
        var dGuNm: String = "",
        @SerializedName("foodSeq")
        var foodSeq: String = "",
        @SerializedName("name")
        var name: String = "",
        @SerializedName("telCode")
        var telCode: String = "",
        @SerializedName("telKuk")
        var telKuk: String = "",
        @SerializedName("telNo")
        var telNo: String = "",
    )
}