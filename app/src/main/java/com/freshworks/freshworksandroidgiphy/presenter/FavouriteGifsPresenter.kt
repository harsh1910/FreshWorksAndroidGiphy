package com.freshworks.freshworksandroidgiphy.presenter

import com.freshworks.freshworksandroidgiphy.network.ServiceRegistry
import com.freshworks.freshworksandroidgiphy.interfaces.IFavouriteGifFile
import java.io.File

class FavouriteGifsPresenter(private val iFavouriteGifFile: IFavouriteGifFile) {

    fun getFavouriteGifs() {
        val favouriteGifsList = ServiceRegistry.getInstance().getFileManager().getFavouriteGifsList(ServiceRegistry.getInstance().getContextApplication())
        if (favouriteGifsList!!.toMutableList().size == 0)
            iFavouriteGifFile.showNoFavouriteGifs()
        else
            iFavouriteGifFile.setFavouriteGifs(favouriteGifsList.toMutableList())
    }

    fun deleteFile(fileName: File): Boolean {
        return ServiceRegistry.getInstance().getFileManager().deleteFavouriteGifFile(fileName)
    }

}