package com.freshworks.freshworksandroidgiphy.utils

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.widget.AbsListView

abstract class InfiniteScrollListener(val layoutManager: StaggeredGridLayoutManager) : RecyclerView.OnScrollListener() {

    var isScrolling: Boolean = false
    private var currentItems = 0
    private var totalItems: Int = 0
    private var scrolledOutItems: Int = 0
    var offSet: Int = 0
    var page: Int = 1

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
            isScrolling = true
        }
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        currentItems = layoutManager.childCount
        totalItems = layoutManager.itemCount

        var firstVisibleItems: IntArray? = null
        firstVisibleItems = layoutManager.findFirstVisibleItemPositions(firstVisibleItems)
        if (firstVisibleItems != null && firstVisibleItems.size > 0) {
            scrolledOutItems = firstVisibleItems[0]
        }

        /*
        * offset set by pages, every page has 15 data
        *
        * and call onLoadMore to get more data
        *
        * */
        if (isScrolling && (currentItems + scrolledOutItems == totalItems)) {
            isScrolling = false
            page += 1
            offSet = (page - 1) * 15 + 1
            onLoadMore(offSet)
        }

    }

    // Defines the process for actually loading more data based on page
    abstract fun onLoadMore(offset: Int)

}