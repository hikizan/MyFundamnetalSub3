package com.hikizan.myfundamentalsubtwo.ui

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.hikizan.myfundamentalsubtwo.R
import com.hikizan.myfundamentalsubtwo.adapter.GithubUserAdapter
import com.hikizan.myfundamentalsubtwo.contract.UsersContract
import com.hikizan.myfundamentalsubtwo.databinding.ActivityMainBinding
import com.hikizan.myfundamentalsubtwo.model.detail.ResponseDetail
import com.hikizan.myfundamentalsubtwo.model.followers.ResponseFollowers
import com.hikizan.myfundamentalsubtwo.model.following.ResponseFollowing
import com.hikizan.myfundamentalsubtwo.model.search.ResponseSearch
import com.hikizan.myfundamentalsubtwo.model.users.ResponseUsers
import com.hikizan.myfundamentalsubtwo.presenter.UsersPresenter
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), UsersContract.usersView {

    private lateinit var presenterUsers: UsersContract.usersPresenter
    private val listDetail: ArrayList<ResponseDetail> = ArrayList<ResponseDetail>()
    private var iSearch: SearchView? = null
    private lateinit var adapter: GithubUserAdapter

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPresenter()

        iSearch = findViewById(R.id.i_search)
        supportActionBar?.title = "List Github User"

        binding.rvGithubUser.setHasFixedSize(true)

        presenterUsers.getListUser()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.i_search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                //mencari list user github
                if (query.isNotEmpty()) {
                    showLoading(true)
                    listDetail.clear()
                    presenterUsers.getSearch(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    private fun initPresenter() {
        showLoading(true)
        presenterUsers = UsersPresenter(this)
    }

    private fun getReqDetail(login: String) {
        presenterUsers.getDetailUser(login)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.pbMain.visibility = View.VISIBLE
        } else {
            binding.pbMain.visibility = View.INVISIBLE
        }
    }

    override fun _onSuccess(usersResponse: List<ResponseUsers>?) {
        for (user in usersResponse!!) {
            getReqDetail(user!!.login)
        }
        showLoading(false)
    }

    override fun _onFailed(message: String?) {
        Toast.makeText(this, "Message: $message", Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun _onSuccessDetail(detailResponse: ResponseDetail?) {
        listDetail.add(detailResponse!!)
        adapter = GithubUserAdapter(listDetail)
        binding.rvGithubUser.layoutManager = LinearLayoutManager(this)
        binding.rvGithubUser.adapter = adapter
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCallback(object :
            GithubUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ResponseDetail) { //masukkan data list following dan followers disini
                showSelectedGithubUser(data)
            }
        })
    }

    override fun _onFailedDetail(message: String?) {
        Toast.makeText(this, "Message: $message", Toast.LENGTH_SHORT).show()
    }

    override fun _onSuccessSearch(searchResponse: ResponseSearch?) {
        for (item in searchResponse!!.items) {
            getReqDetail(item.login)
        }
        showLoading(false)
    }

    override fun _onFailedSearch(message: String?) {
        Toast.makeText(this, "Message: $message", Toast.LENGTH_SHORT).show()
    }

    private fun showSelectedGithubUser(data: ResponseDetail) {
        val moveWithDataParcel = Intent(this@MainActivity, DetailActivity::class.java)
        moveWithDataParcel.putExtra(DetailActivity.EXTRA_DATA, data)
        startActivity(moveWithDataParcel)
    }

}