package com.freshworks.freshworksandroidgiphy.view.fragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.freshworks.freshworksandroidgiphy.R
import com.freshworks.freshworksandroidgiphy.adapter.FavouriteGifsAdapter
import com.freshworks.freshworksandroidgiphy.utils.GRID_SPAN
import com.freshworks.freshworksandroidgiphy.databinding.FragmentFavouriteGifBinding
import com.freshworks.freshworksandroidgiphy.interfaces.IFavouriteGifFile
import com.freshworks.freshworksandroidgiphy.presenter.FavouriteGifsPresenter
import com.freshworks.freshworksandroidgiphy.utils.SpacesItemDecoration
import java.io.File

class FavouriteGifsFragment : Fragment(), IFavouriteGifFile {

    private lateinit var binding: FragmentFavouriteGifBinding
    private lateinit var favouriteGifsPresenter: FavouriteGifsPresenter
    private lateinit var favouriteGifsAdapter: FavouriteGifsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favourite_gif, container, false)
        init()
        return binding.root
    }

    private fun init() {
        favouriteGifsPresenter = FavouriteGifsPresenter(this)
        favouriteGifsAdapter = FavouriteGifsAdapter(activity!!, this)
        setupRecyclerView()

//        get favourite gifs data
        favouriteGifsPresenter.getFavouriteGifs()
    }

    private fun setupRecyclerView() {
        val layoutManager = StaggeredGridLayoutManager(GRID_SPAN, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerViewFavouriteGif.layoutManager = layoutManager
        binding.recyclerViewFavouriteGif.adapter = favouriteGifsAdapter
        val decoration = SpacesItemDecoration(resources.getDimension(R.dimen.item_space).toInt())
        binding.recyclerViewFavouriteGif.addItemDecoration(decoration)
        binding.recyclerViewFavouriteGif.setHasFixedSize(true)
        binding.recyclerViewFavouriteGif.itemAnimator = null
    }

    //    refresh data on change page or tabs click
    fun refreshData() {
        favouriteGifsPresenter.getFavouriteGifs()
    }

    override fun setFavouriteGifs(favouriteGifList: MutableList<File>) {
        binding.tvNoFavouriteGifs.visibility = View.GONE
        favouriteGifsAdapter.setFavouriteGifList(favouriteGifList)
    }

    override fun showNoFavouriteGifs() {
        binding.tvNoFavouriteGifs.visibility = View.VISIBLE
    }

    override fun deleteFile(gifFile: File): Boolean {
        return favouriteGifsPresenter.deleteFile(gifFile)
    }
}
