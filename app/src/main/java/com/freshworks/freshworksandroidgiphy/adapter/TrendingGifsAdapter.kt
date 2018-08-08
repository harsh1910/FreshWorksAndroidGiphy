package com.freshworks.freshworksandroidgiphy.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.freshworks.freshworksandroidgiphy.R
import com.freshworks.freshworksandroidgiphy.databinding.RowTrendingGifBinding
import com.freshworks.freshworksandroidgiphy.interfaces.ITrendingGifFile
import com.freshworks.freshworksandroidgiphy.network.model.Data
import com.freshworks.freshworksandroidgiphy.network.model.TrendingGifsModel
import com.freshworks.freshworksandroidgiphy.utils.*
import com.freshworks.freshworksandroidgiphy.view.activity.LandingActivity
import com.freshworks.freshworksandroidgiphy.view.fragment.TrendingGifsFragment
import com.koushikdutta.ion.Ion


class TrendingGifsAdapter(private val context: Context, trendingGifsFragment: TrendingGifsFragment) : RecyclerView.Adapter<TrendingGifsAdapter.TrendingGifsViewHolder>() {
    private var gifList: MutableList<Data> = mutableListOf()
    private var iTrendingGifFile: ITrendingGifFile = trendingGifsFragment

    //  set gif data
    fun setGifList(gifData: TrendingGifsModel?) {
        gifList = gifData!!.data!!
        notifyDataSetChanged()
    }

    //    load more gifs
    fun addGifList(gifData: TrendingGifsModel?) {
        val size = gifList.size
        gifList.addAll(gifData!!.data!!)
        notifyItemRangeInserted(size, gifList.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingGifsViewHolder {
        val layoutInflater = LayoutInflater.from(context)

        val binding = DataBindingUtil.inflate<RowTrendingGifBinding>(layoutInflater, R.layout.row_trending_gif, parent, false)
        return TrendingGifsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrendingGifsViewHolder, position: Int) {
        val type = position % MODULO
        //For each row respectively
        when (type) {
            GRID_ROW_COLOR_1 -> holder.binding.root.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGridRow1))
            GRID_ROW_COLOR_2 -> holder.binding.root.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGridRow2))
            GRID_ROW_COLOR_3 -> holder.binding.root.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGridRow3))
            GRID_ROW_COLOR_4 -> holder.binding.root.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGridRow4))
        }


        holder.binding.imgFavourite.setImageResource(R.drawable.ic_favorite_border_white)
        holder.binding.imgFavourite.tag = R.drawable.ic_favorite_border_white
        Ion.with(context)
                .load(gifList.get(position).images!!.fixedwidthDownsampled!!.url)
                .intoImageView(holder.binding.imgTrendingGif)

        /*
        *
        * check for gif
        * it is stored on local device or not
        *
        * */
        val favouriteGifs = iTrendingGifFile.getFavouriteGifs()
        if (favouriteGifs.contains(gifList.get(position).id)) {
            holder.binding.imgFavourite.setImageResource(R.drawable.ic_favourite_red)
            holder.binding.imgFavourite.tag = R.drawable.ic_favourite_red
        }

        holder.binding.imgFavourite.setOnClickListener(View.OnClickListener {
            /*
            *
            * if gif is stored on local device,
            *
            * then it will remove from device
            *
            * else store gif in local device
            *
            * */

            if (holder.binding.imgFavourite.tag as Int == R.drawable.ic_favourite_red) {
                if (iTrendingGifFile.deleteFile(gifList.get(position).id.toString())) {
                    holder.binding.imgFavourite.setImageResource(R.drawable.ic_favorite_border_white)
                    holder.binding.imgFavourite.tag = R.drawable.ic_favorite_border_white
                } else {
                    DialogHelper.showDialog(context.getString(R.string.error), context.getString(R.string.please_try_again), context)
                }
            } else {
                iTrendingGifFile.downloadGif(gifList.get(position).id.toString(), gifList.get(position).images!!.fixedwidthDownsampled!!.url)
                val loadAnimation = AnimationUtils.loadAnimation(context,
                        R.anim.zoom_in)
                holder.binding.imgFavourite.setImageResource(R.drawable.ic_favourite_red)
                holder.binding.imgFavourite.startAnimation(loadAnimation)
                loadAnimation.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(arg0: Animation) {}
                    override fun onAnimationRepeat(arg0: Animation) {}
                    override fun onAnimationEnd(arg0: Animation) {
                        holder.binding.imgFavourite.startAnimation(AnimationUtils.loadAnimation(context, R.anim.zoom_out))
                    }
                })
                holder.binding.imgFavourite.tag = R.drawable.ic_favourite_red
            }

//          refresh favourite gif fragment
            if (context is LandingActivity) {
                context.refreshFavouriteGifFragment()
            }
        })
    }

    override fun getItemCount(): Int {
        return gifList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class TrendingGifsViewHolder(bindingData: RowTrendingGifBinding) : RecyclerView.ViewHolder(bindingData.getRoot()) {
        val binding = bindingData
    }
}
