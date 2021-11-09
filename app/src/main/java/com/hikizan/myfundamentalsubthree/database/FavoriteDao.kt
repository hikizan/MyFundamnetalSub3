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

    @Query("SELECT id,login from favorite WHERE login = :login")
    fun detailUserFavorited(login: String): Favorite?

    @Query("SELECT * from favorite WHERE login = :login")
    fun getFavoriteUser(login: String): LiveData<Favorite>

    /*
    @Query("DELETE from favorite WHERE login = :login")
    fun deleteWithUsername(login: String): Favorite
     */
}