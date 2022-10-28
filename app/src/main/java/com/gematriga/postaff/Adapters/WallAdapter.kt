package com.gematriga.postaff.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.gematriga.postaff.Models.Photo
import com.gematriga.postaff.WallpaperActivity
import com.gematriga.postaff.databinding.ItemWallpaperBinding

class WallAdapter : RecyclerView.Adapter<WallAdapter.WallpaperViewHolder>() {

    var list = ArrayList<Photo>()
    lateinit var contexta: Context

    fun setWallpaperData(list : ArrayList<Photo>, context: Context){

        this.list = list
        contexta = context

        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpaperViewHolder {

        return WallpaperViewHolder(ItemWallpaperBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }

    override fun onBindViewHolder(holder: WallpaperViewHolder, position: Int) {
        Glide.with(holder.itemView).load(list[position].src.portrait).into(holder.binding.wallpaperView)

        holder.itemView.setOnClickListener {

            val intent = Intent(contexta,WallpaperActivity::class.java)
            intent.putExtra("URL",list[position].src.portrait)
            contexta.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class WallpaperViewHolder(val binding : ItemWallpaperBinding) : ViewHolder(binding.root){



    }
}