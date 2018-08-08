package com.freshworks.freshworksandroidgiphy.network.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TrendingGifsModel {
    @Expose
    @SerializedName("data")
    var data: MutableList<Data>? = null

    @Expose
    @SerializedName("pagination")
    var pagination: Pagination? = null

    @Expose
    @SerializedName("meta")
    var meta: Meta? = null

    override fun toString(): String {
        return "TrendingGifsModel{" +
                "data=" + data +
                ", pagination=" + pagination +
                ", meta=" + meta +
                '}'.toString()
    }
}

