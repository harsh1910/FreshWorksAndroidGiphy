package com.freshworks.freshworksandroidgiphy.interfaces

import com.freshworks.freshworksandroidgiphy.network.model.TrendingGifsModel

interface ITrendingGif {
    fun setTrendingGifs(gifData: TrendingGifsModel?)
    fun loadMoreTrendingGifs(gifData: TrendingGifsModel?)
    fun showNoGifFound()
}