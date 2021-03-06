package com.hikizan.myfundamentalsubthree.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: Favorite?)

    @Delete
    fun delete(favorite: Favorite?)

    @Query("SELECT * from favorite ORDER BY id DESC")
    fun getAllFavorites(): LiveData<List<Favorite>>

    @Query("SELECT * from favorite WHERE login = :username")
    fun getFavoritedUser(username: String): Favorite?

    @Query("SELECT * from favorite WHERE login = :login")
    fun findSpecificUser(login: String?): LiveData<Favorite>?
}