package com.hikizan.myfundamentalsubthree.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hikizan.myfundamentalsubthree.database.Favorite
import com.hikizan.myfundamentalsubthree.repository.FavoriteRepository

class DetailViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun insert(favorite: Favorite?) {
        mFavoriteRepository.insert(favorite)
    }

    fun delete(favorite: Favorite?) {
        mFavoriteRepository.delete(favorite)
        Log.d("DetailViewModel", "delete: $favorite")
    }

    fun findSpecificUser(login: String?): LiveData<Favorite>? =
        mFavoriteRepository.findSpecificUser(login.toString())

    fun data(username: String): Unit = mFavoriteRepository.getFavoritedUser(username)

    //fun pullFavoritedUser(username: String): Favorite = mFavoriteRepository.pullFavoritedUser(username)

    val isFavorited: LiveData<Boolean> = mFavoriteRepository.isFavorited

}