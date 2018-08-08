package com.freshworks.freshworksandroidgiphy.view.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import com.freshworks.freshworksandroidgiphy.R
import com.freshworks.freshworksandroidgiphy.adapter.ViewPagerAdapter
import com.freshworks.freshworksandroidgiphy.utils.FIRST_TAB_POSITION
import com.freshworks.freshworksandroidgiphy.utils.SECOND_TAB_POSITION
import com.freshworks.freshworksandroidgiphy.databinding.ActivityLandingBinding
import com.freshworks.freshworksandroidgiphy.view.fragment.FavouriteGifsFragment
import com.freshworks.freshworksandroidgiphy.view.fragment.TrendingGifsFragment


class LandingActivity : BaseActivity() {

    private lateinit var binding: ActivityLandingBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private var isRefreshFavouriteGifFragment = false
    private var isRefreshTrendingGifFragment = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_landing)
        init()
    }

    private fun init() {
        setupViewPager()
        setupTabs()
        setSupportActionBar(binding.toolbar)
    }

//    setup viewpager
    private fun setupViewPager() {
        viewPagerAdapter = ViewPagerAdapter(this.supportFragmentManager)
        viewPagerAdapter.addFragment(TrendingGifsFragment())
        viewPagerAdapter.addFragment(FavouriteGifsFragment())
        binding.viewPager.adapter = viewPagerAdapter
        binding.viewPager.addOnPageChangeListener(pageChangeListener)
    }

//    setup tab
    private fun setupTabs() {
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.tabLayout.getTabAt(FIRST_TAB_POSITION)!!.setIcon(R.drawable.ic_home)
        binding.tabLayout.getTabAt(SECOND_TAB_POSITION)!!.setIcon(R.drawable.ic_favorite)
        binding.tabLayout.addOnTabSelectedListener(tabSelectedListener)
    }

    /*
    *  set isRefreshFavouriteGifFragment = true
    *
    *  for refresh favourite gif fragment
    *
    *  it is called from TrendingGifAdapter
    *
    * */
    fun refreshFavouriteGifFragment() {
        isRefreshFavouriteGifFragment = true
    }

    /*
    *  set isRefreshTrendingGifFragment = true
    *
    *  for refresh trending gif fragment
    *
    *  it is called from FavouriteGifAdapter
    *
    * */
    fun refreshTrendingGifFragment() {
        isRefreshTrendingGifFragment = true
    }

    //    viewpager change listener
    private val pageChangeListener = object : ViewPager.OnPageChangeListener {

        override fun onPageScrollStateChanged(state: Int) {
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            /*
            *  when change view pager pages
            *
            * */
            if (position == SECOND_TAB_POSITION) {
                /*
                *  if isRefreshFavouriteGifFragment=true then you have to update
                *  favourite gifs data
                *
                * */
                if (isRefreshFavouriteGifFragment) {
                    isRefreshFavouriteGifFragment = false
                    (viewPagerAdapter.getItem(position) as FavouriteGifsFragment).refreshData()
                }
            } else {
                /*
                *  if isRefreshTrendingGifFragment=true then you have to update
                *  trending gifs data
                *
                * */
                if (isRefreshTrendingGifFragment) {
                    isRefreshTrendingGifFragment = false
                    (viewPagerAdapter.getItem(position) as TrendingGifsFragment).refreshData()
                }
            }

        }

        override fun onPageSelected(position: Int) {

        }
    }

    //    tab listener
    private val tabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) {

            /*
           * on click of first and second tab
           *
           * for refresh data
           *
           * */
            if (tab.position == SECOND_TAB_POSITION) {
                binding.tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(baseContext, R.color.colorFavouriteTabIndicator))
                if (isRefreshFavouriteGifFragment) {
                    isRefreshFavouriteGifFragment = false
                    (viewPagerAdapter.getItem(tab.position) as FavouriteGifsFragment).refreshData()
                }
            } else {
                binding.tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(baseContext, R.color.colorHomeTabIndicator))

                if (isRefreshTrendingGifFragment) {
                    isRefreshTrendingGifFragment = false
                    (viewPagerAdapter.getItem(tab.position) as TrendingGifsFragment).refreshData()
                }

            }
        }

        override fun onTabUnselected(tab: TabLayout.Tab) {
        }

        override fun onTabReselected(tab: TabLayout.Tab) {

        }
    }
}
