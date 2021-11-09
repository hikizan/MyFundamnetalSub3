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

    fun delete(favorite: Favorite?) {
        mFavoriteRepository.delete(favorite)
    }

    fun detailUserFavorited(login: String?) {
        mFavoriteRepository.detailUserFavorited(login.toString())
    }

    fun getFavoriteUser(login: String?): LiveData<Favorite> = mFavoriteRepository.getFavoriteUser(login.toString())

    /*
    fun deleteWithUsername(login: String) {
        mFavoriteRepository.deleteWithUsername(login)
    }
     */
}