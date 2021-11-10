package com.hikizan.myfundamentalsubthree.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hikizan.myfundamentalsubthree.database.Favorite
import com.hikizan.myfundamentalsubthree.repository.FavoriteRepository

class DetailViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun insert(favorite: Favorite?) {
        mFavoriteRepository.insert(favorite)
    }

    fun delete(favorite: Favorite) {
        mFavoriteRepository.delete(favorite)
    }

    fun findSpecificUser(login: String?): LiveData<Favorite> = mFavoriteRepository.findSpecificUser(login.toString())

    fun data(username: String): Unit = mFavoriteRepository.getFavoritedUser(username)

    val isFavorited: LiveData<Boolean> = mFavoriteRepository.isFavorited

}