package com.freshworks.freshworksandroidgiphy.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Images {
    @Expose
    @SerializedName("fixed_width_downsampled")
    var fixedwidthDownsampled: FixedWidthDownsampled? = null
        private set

    fun setFixedWidthDownsampled(fixedWidthDownsampled: FixedWidthDownsampled) {
        this.fixedwidthDownsampled = fixedWidthDownsampled
    }

    inner class FixedWidthDownsampled {
        @Expose
        @SerializedName("size")
        var size: String? = null
        @Expose
        @SerializedName("height")
        var height: String? = null
        @Expose
        @SerializedName("width")
        var width: String? = null
        @Expose
        @SerializedName("url")
        var url: String? = null

    }


}
