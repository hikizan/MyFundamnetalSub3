package com.hikizan.myfundamentalsubthree.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hikizan.myfundamentalsubthree.database.Favorite
import com.hikizan.myfundamentalsubthree.repository.FavoriteRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun getAllFavorites() : LiveData<List<Favorite>> = mFavoriteRepository.getAllFavorites()
}