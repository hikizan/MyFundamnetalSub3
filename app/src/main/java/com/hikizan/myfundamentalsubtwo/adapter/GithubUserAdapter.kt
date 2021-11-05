package com.hikizan.myfundamentalsubtwo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hikizan.myfundamentalsubtwo.databinding.ItemRowUserBinding
import com.hikizan.myfundamentalsubtwo.model.detail.ResponseDetail

class GithubUserAdapter(private val listDetail: ArrayList<ResponseDetail>) :
    RecyclerView.Adapter<GithubUserAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ResponseDetail)
    }


    class ViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvItemUsername.text = listDetail[position].login
        holder.binding.tvItemName.text = listDetail[position].name

        Glide.with(holder.itemView.context)
            .load(listDetail[position].avatarUrl)
            .circleCrop()
            .into(holder.binding.imgItemPhoto)

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listDetail[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int = listDetail.size
}