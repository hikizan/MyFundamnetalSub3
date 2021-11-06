package com.hikizan.myfundamentalsubthree.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hikizan.myfundamentalsubthree.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private var _activityFavoriteBinding: ActivityFavoriteBinding? = null
    private val binding get() = _activityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityFavoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        supportActionBar?.title = "Favorite User"
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityFavoriteBinding = null
    }
}