package com.hikizan.myfundamentalsubthree.api

import com.hikizan.myfundamentalsubthree.model.detail.ResponseDetail
import com.hikizan.myfundamentalsubthree.model.followers.ResponseFollowers
import com.hikizan.myfundamentalsubthree.model.following.ResponseFollowing
import com.hikizan.myfundamentalsubthree.model.search.ResponseSearch
import com.hikizan.myfundamentalsubthree.model.users.ResponseUsers
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    //search
    @Headers("Authorization: token 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")
    @GET("search/users")
    fun getSearchListUser(
        @Query("q") username: String
    ): Call<ResponseSearch>

    //listAll
    @Headers("Authorization: token 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")
    @GET("users")
    fun getListUser(): Call<List<ResponseUsers>>

    //detail user
    @Headers("Authorization: token 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")
    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<ResponseDetail>

    //follower
    @Headers("Authorization: token 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")
    @GET("users/{username}/followers")
    fun getListFollowers(
        @Path("username") username: String
    ): Call<List<ResponseFollowers>>

    //following
    @Headers("Authorization: token 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")
    @GET("users/{username}/following")
    fun getListFollowing(
        @Path("username") username: String
    ): Call<List<ResponseFollowing>>

}