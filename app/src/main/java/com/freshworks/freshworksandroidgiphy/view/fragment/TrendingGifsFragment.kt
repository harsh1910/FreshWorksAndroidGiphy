package com.freshworks.freshworksandroidgiphy.view.fragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.SearchView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.*
import com.freshworks.freshworksandroidgiphy.R
import com.freshworks.freshworksandroidgiphy.adapter.TrendingGifsAdapter
import com.freshworks.freshworksandroidgiphy.utils.GRID_SPAN
import com.freshworks.freshworksandroidgiphy.utils.START_OFFSET
import com.freshworks.freshworksandroidgiphy.utils.START_PAGE
import com.freshworks.freshworksandroidgiphy.databinding.FragmentTrendingGifBinding
import com.freshworks.freshworksandroidgiphy.interfaces.IShowError
import com.freshworks.freshworksandroidgiphy.interfaces.ITrendingGif
import com.freshworks.freshworksandroidgiphy.interfaces.ITrendingGifFile
import com.freshworks.freshworksandroidgiphy.network.model.TrendingGifsModel
import com.freshworks.freshworksandroidgiphy.presenter.TrendingGifsPresenter
import com.freshworks.freshworksandroidgiphy.utils.DialogHelper
import com.freshworks.freshworksandroidgiphy.utils.InfiniteScrollListener
import com.freshworks.freshworksandroidgiphy.utils.ProgressHelper
import com.freshworks.freshworksandroidgiphy.utils.SpacesItemDecoration


class TrendingGifsFragment : Fragment(), ITrendingGif, IShowError, ITrendingGifFile {

    private lateinit var binding: FragmentTrendingGifBinding
    private lateinit var mSearchView: SearchView
    private lateinit var trendingGifsAdapter: TrendingGifsAdapter
    private lateinit var trendingGifsPresenter: TrendingGifsPresenter
    val layoutManager = StaggeredGridLayoutManager(GRID_SPAN, StaggeredGridLayoutManager.VERTICAL)

    private val endlessScrollListener = object : InfiniteScrollListener(layoutManager) {
        override fun onLoadMore(offset: Int) {
            binding.progressBar.visibility = View.VISIBLE
            trendingGifsPresenter.getLoadMoreTrendingGifs(offSet)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_trending_gif, container, false)
        init()
        return binding.root
    }


    private fun init() {
        trendingGifsAdapter = TrendingGifsAdapter(activity!!, this)
        trendingGifsPresenter = TrendingGifsPresenter(this, this, this)

        setupRecyclerView()

//        get trending gifs data
        ProgressHelper.showDialog(activity!!)
        trendingGifsPresenter.getTrendingGifs(START_OFFSET)

        setHasOptionsMenu(true)
    }

    //    setup searchview
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        activity!!.menuInflater.inflate(R.menu.menu_toolbar, menu)
        val mSearch = menu!!.findItem(R.id.action_search)
        mSearchView = mSearch.actionView as SearchView
        mSearchView.queryHint = getString(R.string.search_all_the_gif)

        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            /*
            * onClick of keyboard event,
            *
            * get Search data
            *
            * */
            override fun onQueryTextSubmit(query: String): Boolean {
                if (!query.isEmpty()) {
                    ProgressHelper.showDialog(activity!!)
                    mSearchView.clearFocus()
                    endlessScrollListener.page = START_PAGE
                    trendingGifsPresenter.searchTrendingGifs(query.trim(), START_OFFSET)
                }
                return true
            }

            /*
            * when search view is empty,
            *
            * get trending data
            *
            * */
            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    binding.tvNoGifFound.visibility = View.GONE
                    endlessScrollListener.page = START_PAGE
                    trendingGifsPresenter.getTrendingGifs(START_OFFSET)
                }
                return true
            }
        })
    }

    private fun setupRecyclerView() {
        binding.recyclerViewTrendingGif.layoutManager = layoutManager
        binding.recyclerViewTrendingGif.adapter = trendingGifsAdapter
        val decoration = SpacesItemDecoration(resources.getDimension(R.dimen.item_space).toInt())
        binding.recyclerViewTrendingGif.addItemDecoration(decoration)
        binding.recyclerViewTrendingGif.setHasFixedSize(true)
        binding.recyclerViewTrendingGif.itemAnimator = null
        binding.recyclerViewTrendingGif.addOnScrollListener(endlessScrollListener)
    }

//    refresh data on change page or tabs click
    fun refreshData() {
        binding.progressBar.visibility = View.VISIBLE
        if (mSearchView.query.toString().isNotEmpty()) {
            endlessScrollListener.page = START_PAGE
            trendingGifsPresenter.searchTrendingGifs(mSearchView.query.toString().trim(), START_OFFSET)
        } else {
            endlessScrollListener.page = START_PAGE
            trendingGifsPresenter.getTrendingGifs(START_OFFSET)
        }
    }

    override fun setTrendingGifs(gifData: TrendingGifsModel?) {
        ProgressHelper.dismissDialog()
        binding.progressBar.visibility = View.GONE
        binding.tvNoGifFound.visibility = View.GONE
        trendingGifsAdapter.setGifList(gifData)
    }

    override fun loadMoreTrendingGifs(gifData: TrendingGifsModel?) {
        binding.progressBar.visibility = View.GONE
        trendingGifsAdapter.addGifList(gifData)
    }

    override fun showError() {
        DialogHelper.showDialog(activity!!.getString(R.string.error), getString(R.string.network_error), activity!!)
    }

    override fun showNoGifFound() {
        ProgressHelper.dismissDialog()
        binding.tvNoGifFound.visibility = View.VISIBLE
    }

    override fun getFavouriteGifs(): MutableList<String> {
        return trendingGifsPresenter.getFavouriteGifs()
    }

    override fun deleteFile(fileName: String): Boolean {
        return trendingGifsPresenter.deleteFile(fileName)
    }

    override fun downloadGif(fileName: String, gifUrl: String?) {
        trendingGifsPresenter.downloadGif(fileName, gifUrl)
    }

    override fun gifDownloadFailed() {
        DialogHelper.showDialog(activity!!.getString(R.string.error), activity!!.getString(R.string.please_try_again), activity!!)
    }

}
