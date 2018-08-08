package com.freshworks.freshworksandroidgiphy.interfaces

import java.io.File

interface IFavouriteGifFile {
    fun setFavouriteGifs(favouriteGifList: MutableList<File>)
    fun deleteFile(gifFile: File): Boolean
    fun showNoFavouriteGifs()
}
