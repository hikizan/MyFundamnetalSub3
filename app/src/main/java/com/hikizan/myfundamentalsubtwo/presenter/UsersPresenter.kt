package com.hikizan.myfundamentalsubtwo.presenter

import com.hikizan.myfundamentalsubtwo.api.ApiConfig
import com.hikizan.myfundamentalsubtwo.contract.UsersContract
import com.hikizan.myfundamentalsubtwo.model.detail.ResponseDetail
import com.hikizan.myfundamentalsubtwo.model.followers.ResponseFollowers
import com.hikizan.myfundamentalsubtwo.model.following.ResponseFollowing
import com.hikizan.myfundamentalsubtwo.model.search.ResponseSearch
import com.hikizan.myfundamentalsubtwo.model.users.ResponseUsers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersPresenter : UsersContract.usersPresenter {

    private lateinit var view: UsersContract.usersView
    private lateinit var apiConfig: ApiConfig

    constructor(viewConst: UsersContract.usersView) {
        view = viewConst
        apiConfig = ApiConfig()
    }

    override fun getListUser() {
        apiConfig.getApiService().getListUser().enqueue(object : Callback<List<ResponseUsers>> {
            override fun onResponse(
                call: Call<List<ResponseUsers>>,
                response: Response<List<ResponseUsers>>
            ) {
                when (response.code()) {
                    200 -> view._onSuccess(response.body())
                    404 -> view._onFailed("Gagal Memuat Data List User")
                    500 -> view._onFailed("Gagal Memuat Data List User")
                    else -> view._onFailed("Gagal Memuat Data List User")
                }
            }

            override fun onFailure(call: Call<List<ResponseUsers>>, t: Throwable) {
                view._onFailed("Pastikan Jaringan Anda Stabil")
            }

        })
    }

    override fun getDetailUser(username: String?) {
        apiConfig.getApiService().getDetailUser(username!!)
            .enqueue(object : Callback<ResponseDetail> {
                override fun onResponse(
                    call: Call<ResponseDetail>,
                    response: Response<ResponseDetail>
                ) {
                    when (response.code()) {
                        200 -> view._onSuccessDetail(response.body())
                        404 -> view._onFailedDetail("Gagal Memuat Data Detail User")
                        500 -> view._onFailedDetail("Gagal Memuat Data Detail User")
                        else -> view._onFailedDetail("Gagal Memuat Data Detail User")
                    }
                }

                override fun onFailure(call: Call<ResponseDetail>, t: Throwable) {
                    view._onFailedDetail("Pastikan Jaringan Anda Stabil")
                }

            })
    }

    override fun getSearch(username: String?) {
        apiConfig.getApiService().getSearchListUser(username!!)
            .enqueue(object : Callback<ResponseSearch> {
                override fun onResponse(
                    call: Call<ResponseSearch>,
                    response: Response<ResponseSearch>
                ) {
                    when (response.code()) {
                        200 -> view._onSuccessSearch(response.body())
                        404 -> view._onFailedSearch("Gagal Memuat Data Search")
                        500 -> view._onFailedSearch("Gagal Memuat Data Search")
                        else -> view._onFailedSearch("Gagal Memuat Data Search")
                    }
                }

                override fun onFailure(call: Call<ResponseSearch>, t: Throwable) {
                    view._onFailedSearch("Pastikan Jaringan Anda Stabil")
                }

            })
    }
}