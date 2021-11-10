package com.hikizan.myfundamentalsubthree.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hikizan.myfundamentalsubthree.database.Favorite
import com.hikizan.myfundamentalsubthree.database.FavoriteDao
import com.hikizan.myfundamentalsubthree.database.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    private val favorited: MutableLiveData<Boolean> = MutableLiveData()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteDao()
    }

    fun getAllFavorites(): LiveData<List<Favorite>> = mFavoriteDao.getAllFavorites()

    fun insert(favorite: Favorite?) {
        executorService.execute { mFavoriteDao.insert(favorite) }
    }

    fun delete(favorite: Favorite) {
        executorService.execute { mFavoriteDao.delete(favorite) }
    }

    fun getFavoritedUser(username: String) {
        executorService.execute {
            val favorite = mFavoriteDao.getFavoritedUser(username)
            if (favorite != null) {
                favorited.postValue(true)
            } else {
                favorited.postValue(false)
            }
        }
    }

    fun findSpecificUser(login: String?): LiveData<Favorite> = mFavoriteDao.findSpecificUser(login)

    val isFavorited: LiveData<Boolean> = favorited
}