package com.freshworks.freshworksandroidgiphy.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.freshworks.freshworksandroidgiphy.R
import com.freshworks.freshworksandroidgiphy.databinding.RowFavouriteGifBinding
import com.freshworks.freshworksandroidgiphy.interfaces.IFavouriteGifFile
import com.freshworks.freshworksandroidgiphy.utils.*
import com.freshworks.freshworksandroidgiphy.view.activity.LandingActivity
import com.freshworks.freshworksandroidgiphy.view.fragment.FavouriteGifsFragment
import com.koushikdutta.ion.Ion
import java.io.File


class FavouriteGifsAdapter(private val context: Context, favouriteGifsFragment: FavouriteGifsFragment) : RecyclerView.Adapter<FavouriteGifsAdapter.MyViewHolder>() {
    private var gifList: MutableList<File> = mutableListOf()
    private var iFavouriteGifFile: IFavouriteGifFile = favouriteGifsFragment

    fun setFavouriteGifList(favouriteGifs: MutableList<File>) {
        gifList = favouriteGifs
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(context)

        val binding = DataBindingUtil.inflate<RowFavouriteGifBinding>(layoutInflater, R.layout.row_favourite_gif, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val type = position % MODULO
        //For each row respectively
        when (type) {
            GRID_ROW_COLOR_1 -> holder.binding.root.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGridRow1))
            GRID_ROW_COLOR_2 -> holder.binding.root.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGridRow2))
            GRID_ROW_COLOR_3 -> holder.binding.root.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGridRow3))
            GRID_ROW_COLOR_4 -> holder.binding.root.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGridRow4))
        }

        Ion.with(context).load(gifList.get(position)).intoImageView(holder.binding.imgTrendingGif)

        holder.binding.imgFavourite.setOnClickListener(View.OnClickListener {
            /*
            *  remove gif from local device
            *
            * */
            if (iFavouriteGifFile.deleteFile(gifList.get(position))) {
                gifList.removeAt(position)

//                when all gifs are removed from local device, will show no favourite gifs
                if(gifList.size==0)
                    iFavouriteGifFile.showNoFavouriteGifs()
                notifyItemRemoved(position)
            } else {
                DialogHelper.showDialog(context.getString(R.string.error), context.getString(R.string.please_try_again), context)
            }

            //     refresh trending gif fragment
            if (context is LandingActivity) {
                context.refreshTrendingGifFragment()
            }
        })

    }

    override fun getItemCount(): Int {
        return gifList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class MyViewHolder(bindingData: RowFavouriteGifBinding) : RecyclerView.ViewHolder(bindingData.getRoot()) {
        val binding = bindingData
    }
}
