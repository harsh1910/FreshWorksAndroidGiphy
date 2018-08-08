package com.freshworks.freshworksandroidgiphy.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Meta {
    @Expose
    @SerializedName("status")
    var status: Int = 0

    @Expose
    @SerializedName("msg")
    var msg: String? = null

    @Expose
    @SerializedName("response_id")
    var responseId: String? = null

    override fun toString(): String {
        return "Meta{" +
                "status=" + status +
                ", msg='" + msg + '\''.toString() +
                ", responseId='" + responseId + '\''.toString() +
                '}'.toString()
    }
}
