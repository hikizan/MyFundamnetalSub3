package com.hikizan.myfundamentalsubthree.helper

import androidx.recyclerview.widget.DiffUtil
import com.hikizan.myfundamentalsubthree.database.Favorite

class FavoriteDiffCallback(private val mOldFavoriteList: List<Favorite>, private val mNewFavoriteList: List<Favorite>) : DiffUtil.Callback(){
    override fun getOldListSize(): Int {
        return mOldFavoriteList.size
    }

    override fun getNewListSize(): Int {
        return mNewFavoriteList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldFavoriteList[oldItemPosition].id == mNewFavoriteList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFavoriteUser = mOldFavoriteList[oldItemPosition]
        val newFavoriteUser = mNewFavoriteList[newItemPosition]
        return oldFavoriteUser.login == newFavoriteUser.login
    }

}