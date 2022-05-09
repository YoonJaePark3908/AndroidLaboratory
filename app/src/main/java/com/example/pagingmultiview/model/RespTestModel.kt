package com.example.pagingmultiview.model

data class RespTestModel(
    var comMsgHeader: ComMsgHeader = ComMsgHeader(),
    var msgHeader: MsgHeader = MsgHeader(),
    var msgBodyList: List<MsgBody> = listOf()
) {
    data class ComMsgHeader(
        var returnCode: String = "",
        var returnMessage: String = "",
        var successYN: String = ""
    )

    data class MsgHeader(
        var pageNo: Int = 0,
        var totalPage: Int = 0,
        var numOfRows: Int = 0,
        var totalCount: Int = 0
    )

    data class MsgBody(
        var id: String = "",
        var addr1: String = "",
        var addr2: String = "",
        var cGubun: String = "",
        var cid: String = "",
        var dCode: String = "",
        var dLang: String = "",
        var expression: String = "",
        var hitCnt: String = "",
        var idxImgName: String = "",
        var idxImgPath: String = "",
        var imgIdx: String = "",
        var keyword: String = "",
        var name: String = "",
        var recommend: String = "",
        var telCode: String = "",
        var telKuk: String = "",
        var telNo: String = "",
        var tourSeq: String = "",
        var useYn: String = "",
        var zipcode: String = ""
    )
}