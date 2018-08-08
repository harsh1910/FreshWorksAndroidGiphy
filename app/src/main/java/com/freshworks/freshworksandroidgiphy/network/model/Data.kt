package com.freshworks.freshworksandroidgiphy.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Data {

    @Expose
    @SerializedName("images")
    var images: Images? = null
    @Expose
    @SerializedName("user")
    var user: User? = null
    @Expose
    @SerializedName("trending_datetime")
    var trendingDatetime: String? = null
    @Expose
    @SerializedName("import_datetime")
    var importDatetime: String? = null
    @Expose
    @SerializedName("is_sticker")
    var isSticker: Int = 0
    @Expose
    @SerializedName("source_post_url")
    var sourcePostUrl: String? = null
    @Expose
    @SerializedName("source_tld")
    var sourceTld: String? = null
    @Expose
    @SerializedName("content_url")
    var contentUrl: String? = null
    @Expose
    @SerializedName("rating")
    var rating: String? = null
    @Expose
    @SerializedName("source")
    var source: String? = null
    @Expose
    @SerializedName("username")
    var username: String? = null
    @Expose
    @SerializedName("embed_url")
    var embedUrl: String? = null
    @Expose
    @SerializedName("bitly_url")
    var bitlyUrl: String? = null
    @Expose
    @SerializedName("bitly_gif_url")
    var bitlyGifUrl: String? = null
    @Expose
    @SerializedName("url")
    var url: String? = null
    @Expose
    @SerializedName("slug")
    var slug: String? = null
    @Expose
    @SerializedName("id")
    var id: String? = null
    @Expose
    @SerializedName("type")
    var type: String? = null

    class User {
        @Expose
        @SerializedName("guid")
        var guid: String? = null
        @Expose
        @SerializedName("display_name")
        var displayName: String? = null
        @Expose
        @SerializedName("username")
        var username: String? = null
        @Expose
        @SerializedName("profile_url")
        var profileUrl: String? = null
        @Expose
        @SerializedName("banner_url")
        var bannerUrl: String? = null
        @Expose
        @SerializedName("avatar_url")
        var avatarUrl: String? = null

        override fun toString(): String {
            return "User{" +
                    "guid='" + guid + '\''.toString() +
                    ", displayName='" + displayName + '\''.toString() +
                    ", username='" + username + '\''.toString() +
                    ", profileUrl='" + profileUrl + '\''.toString() +
                    ", bannerUrl='" + bannerUrl + '\''.toString() +
                    ", avatarUrl='" + avatarUrl + '\''.toString() +
                    '}'.toString()
        }
    }
}
