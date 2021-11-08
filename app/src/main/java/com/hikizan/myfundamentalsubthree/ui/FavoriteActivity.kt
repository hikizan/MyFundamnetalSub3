package com.hikizan.myfundamentalsubthree.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hikizan.myfundamentalsubthree.adapter.FavoriteAdapter
import com.hikizan.myfundamentalsubthree.database.Favorite
import com.hikizan.myfundamentalsubthree.databinding.ActivityFavoriteBinding
import com.hikizan.myfundamentalsubthree.ui.viewmodel.DetailViewModelFactory
import com.hikizan.myfundamentalsubthree.ui.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {

    private var _activityFavoriteBinding: ActivityFavoriteBinding? = null
    private val binding get() = _activityFavoriteBinding

    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityFavoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        supportActionBar?.title = "Favorite User"

        val favoriteViewModel = obtainViewModel(this@FavoriteActivity)
        favoriteViewModel.getAllFavorites().observe(this, { favoriteList ->
            if (favoriteList != null) {
                adapter.setListFavorite(favoriteList)
            }
        })

        adapter = FavoriteAdapter()
        binding?.rvFavuser?.layoutManager = LinearLayoutManager(this@FavoriteActivity)
        binding?.rvFavuser?.setHasFixedSize(true)

        binding?.rvFavuser?.adapter = adapter

        adapter.setOnItemClickCallback(object :
            FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(favorite: Favorite) {
                showSelectedFavoriteUser(favorite)
            }
        })


    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = DetailViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
    }

    private fun showSelectedFavoriteUser(favorite: Favorite) {
        val moveWithParcel = Intent(this@FavoriteActivity, DetailActivity::class.java)
        moveWithParcel.putExtra(DetailActivity.EXTRA_FAVORITE, favorite)
        startActivity(moveWithParcel)
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityFavoriteBinding = null
    }
}