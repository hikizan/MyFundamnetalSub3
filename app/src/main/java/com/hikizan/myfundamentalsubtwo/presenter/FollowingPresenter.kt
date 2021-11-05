package com.hikizan.myfundamentalsubtwo.presenter

import com.hikizan.myfundamentalsubtwo.api.ApiConfig
import com.hikizan.myfundamentalsubtwo.contract.UsersContract
import com.hikizan.myfundamentalsubtwo.model.detail.ResponseDetail
import com.hikizan.myfundamentalsubtwo.model.following.ResponseFollowing
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingPresenter : UsersContract.followingPresenter {

    private lateinit var view: UsersContract.followingView
    private lateinit var apiConfig: ApiConfig

    constructor(viewConst: UsersContract.followingView) {
        view = viewConst
        apiConfig = ApiConfig()
    }

    override fun getFollowing(username: String?) {
        apiConfig.getApiService().getListFollowing(username!!)
            .enqueue(object : Callback<List<ResponseFollowing>> {
                override fun onResponse(
                    call: Call<List<ResponseFollowing>>,
                    response: Response<List<ResponseFollowing>>
                ) {
                    when (response.code()) {
                        200 -> view._onSuccessFollowing(response.body())
                        404 -> view._onFailedFollowing("Gagal Memuat Data Following")
                        500 -> view._onFailedFollowing("Gagal Memuat Data Following")
                        else -> view._onFailedFollowing("Gagal Memuat Data Following")
                    }
                }

                override fun onFailure(call: Call<List<ResponseFollowing>>, t: Throwable) {
                    view._onFailedFollowing("Pastikan Jaringan Anda Stabil")
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
}