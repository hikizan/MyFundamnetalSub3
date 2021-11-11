package com.hikizan.myfundamentalsubthree.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.hikizan.myfundamentalsubthree.database.Favorite
import com.hikizan.myfundamentalsubthree.database.FavoriteDao
import com.hikizan.myfundamentalsubthree.database.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    private var favorited:Boolean = false

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteDao()
    }

    fun getAllFavorites(): LiveData<List<Favorite>> = mFavoriteDao.getAllFavorites()

    fun insert(favorite: Favorite?) {
        executorService.execute { mFavoriteDao.insert(favorite) }
    }

    fun delete(favorite: Favorite?) {
        executorService.execute { mFavoriteDao.delete(favorite)
            Log.d("DetailActivityTo_DELETE", "FromRepository: DELETE SUCCESS")
        }
    }

    fun getFavoritedUser(username: String) {
        executorService.execute {
            val favorite = mFavoriteDao.getFavoritedUser(username)
            if (favorite != null) {
                mFavoriteDao.delete(favorite)
            }
        }
    }

    fun findSpecificUser(login: String?): LiveData<Favorite>? = mFavoriteDao.findSpecificUser(login)

    var isFavorited: Boolean = favorited
}