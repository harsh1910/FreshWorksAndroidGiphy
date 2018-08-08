package com.freshworks.freshworksandroidgiphy.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Pagination {
    @Expose
    @SerializedName("total_count")
    var totalCount: Int = 0

    @Expose
    @SerializedName("count")
    var count: Int = 0

    @Expose
    @SerializedName("offset")
    var offset: Int = 0

    override fun toString(): String {
        return "Pagination{" +
                "totalCount=" + totalCount +
                ", count=" + count +
                ", offset=" + offset +
                '}'.toString()
    }
}
