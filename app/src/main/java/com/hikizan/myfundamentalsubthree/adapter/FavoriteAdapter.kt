package com.hikizan.myfundamentalsubthree.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hikizan.myfundamentalsubthree.database.Favorite
import com.hikizan.myfundamentalsubthree.databinding.ItemRowUserBinding
import com.hikizan.myfundamentalsubthree.helper.FavoriteDiffCallback
import com.hikizan.myfundamentalsubthree.ui.DetailActivity

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    private val listFavorite = ArrayList<Favorite>()

    /*
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
     */

    fun setListFavorite(listFavorite: List<Favorite>){
        val diffCallback = FavoriteDiffCallback(this.listFavorite, listFavorite)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavorite.clear()
        this.listFavorite.addAll(listFavorite)
        diffResult.dispatchUpdatesTo(this)
    }

    /*
    interface OnItemClickCallback {
        fun onItemClicked(favorite: Favorite)
    }
     */

    inner class ViewHolder(private val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(favorite: Favorite){
            with(binding){
                tvItemUsername.text = favorite.login
                tvItemName.text = favorite.name

                Glide.with(itemView.context)
                    .load(favorite.avatarUrl)
                    .circleCrop()
                    .into(imgItemPhoto)

                itemView.setOnClickListener {
                    val intent = Intent(it.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_FAVORITE, favorite)
                    it.context.startActivity(intent)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.ViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.ViewHolder, position: Int) {
        holder.bind(listFavorite[position])
        //holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listFavorite[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int = listFavorite.size
}