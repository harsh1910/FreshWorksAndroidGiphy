package com.freshworks.freshworksandroidgiphy.interfaces

interface ITrendingGifFile {
    fun getFavouriteGifs(): MutableList<String>
    fun deleteFile(fileName: String): Boolean
    fun downloadGif(fileName: String, gifUrl: String?)
    fun gifDownloadFailed()
}