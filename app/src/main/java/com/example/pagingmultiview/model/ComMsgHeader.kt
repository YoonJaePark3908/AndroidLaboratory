package com.example.pagingmultiview.model

import com.google.gson.annotations.SerializedName

data class ComMsgHeader(
    @SerializedName("returnCode")
    var returnCode: String = "",
    @SerializedName("returnMessage")
    var returnMessage: String = "",
    @SerializedName("successYN")
    var successYN: String = ""
)
