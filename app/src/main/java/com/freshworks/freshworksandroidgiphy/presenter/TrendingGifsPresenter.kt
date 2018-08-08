package com.freshworks.freshworksandroidgiphy.presenter

import com.freshworks.freshworksandroidgiphy.network.ServiceRegistry
import com.freshworks.freshworksandroidgiphy.interfaces.IShowError
import com.freshworks.freshworksandroidgiphy.interfaces.ITrendingGif
import com.freshworks.freshworksandroidgiphy.network.model.TrendingGifsModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.freshworks.freshworksandroidgiphy.interfaces.IFileDownloadListener
import com.freshworks.freshworksandroidgiphy.interfaces.ITrendingGifFile
import com.freshworks.freshworksandroidgiphy.utils.API_KEY
import com.freshworks.freshworksandroidgiphy.utils.GIF_LOAD_LIMIT
import com.freshworks.freshworksandroidgiphy.utils.SUCCESS_STATUS

class TrendingGifsPresenter(private val iTrendingGif: ITrendingGif, private val iShowError: IShowError, private val iTrendingGifFile: ITrendingGifFile) : IFileDownloadListener {

    fun getTrendingGifs(offSet: Int) {
        val trendingGifs: Call<TrendingGifsModel> = ServiceRegistry.getInstance().getApi().getTrendingGifs(API_KEY, offSet, GIF_LOAD_LIMIT)

        trendingGifs.enqueue(object : Callback<TrendingGifsModel> {
            override fun onFailure(call: Call<TrendingGifsModel>?, t: Throwable?) {
                iShowError.showError()
            }

            override fun onResponse(call: Call<TrendingGifsModel>?, response: Response<TrendingGifsModel>?) {
                if (response != null && response.isSuccessful && response.body() != null && response.body()!!.meta!!.status == SUCCESS_STATUS) {
                    iTrendingGif.setTrendingGifs(response.body())
                }
            }
        })

    }

    fun getLoadMoreTrendingGifs(offSet: Int) {
        val trendingGifs: Call<TrendingGifsModel> = ServiceRegistry.getInstance().getApi().getTrendingGifs(API_KEY, offSet, GIF_LOAD_LIMIT)

        trendingGifs.enqueue(object : Callback<TrendingGifsModel> {
            override fun onFailure(call: Call<TrendingGifsModel>?, t: Throwable?) {
                iShowError.showError()
            }

            override fun onResponse(call: Call<TrendingGifsModel>?, response: Response<TrendingGifsModel>?) {
                if (response != null && response.isSuccessful && response.body() != null && response.body()!!.meta!!.status == SUCCESS_STATUS) {
                    iTrendingGif.loadMoreTrendingGifs(response.body())
                }
            }
        })

    }

    fun searchTrendingGifs(searchText: String, offSet: Int) {
        val trendingGifs: Call<TrendingGifsModel> = ServiceRegistry.getInstance().getApi().searchTrendingGifs(API_KEY, searchText, offSet, GIF_LOAD_LIMIT)
        trendingGifs.enqueue(object : Callback<TrendingGifsModel> {
            override fun onFailure(call: Call<TrendingGifsModel>?, t: Throwable?) {
                iShowError.showError()
            }

            override fun onResponse(call: Call<TrendingGifsModel>?, response: Response<TrendingGifsModel>?) {
                if (response != null && response.isSuccessful && response.body() != null && response.body()!!.meta!!.status == SUCCESS_STATUS) {
                    if (response.body()!!.data!!.size == 0)
                        iTrendingGif.showNoGifFound()
                    else
                        iTrendingGif.setTrendingGifs(response.body())
                }
            }
        })
    }

    fun getFavouriteGifs(): MutableList<String> {
        val favouriteGifsList = ServiceRegistry.getInstance().getFileManager().getFavouriteGifsList(ServiceRegistry.getInstance().getContextApplication())
        val favouriteList: MutableList<String> = mutableListOf()
        for (i in favouriteGifsList!!.indices) {
            favouriteList.add(favouriteGifsList[i].name.substring(0, favouriteGifsList[i].name.length - 4))
        }
        return favouriteList
    }

    fun deleteFile(fileName: String): Boolean {
        return ServiceRegistry.getInstance().getFileManager().deleteFile(ServiceRegistry.getInstance().getContextApplication(), fileName)
    }

    fun downloadGif(fileName: String, gifUrl: String?) {
        ServiceRegistry.getInstance().getFileManager().downloadingFile(ServiceRegistry.getInstance().getContextApplication(), fileName, gifUrl, this)
    }

    override fun downloadFailed() {
        iTrendingGifFile.gifDownloadFailed()
    }

}