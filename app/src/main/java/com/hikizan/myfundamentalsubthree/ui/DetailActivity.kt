package com.hikizan.myfundamentalsubthree.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.hikizan.myfundamentalsubthree.R
import com.hikizan.myfundamentalsubthree.adapter.SectionsPagerAdapter
import com.hikizan.myfundamentalsubthree.database.Favorite
import com.hikizan.myfundamentalsubthree.databinding.ActivityDetailBinding
import com.hikizan.myfundamentalsubthree.model.detail.ResponseDetail
import com.hikizan.myfundamentalsubthree.ui.viewmodel.DetailViewModel
import com.hikizan.myfundamentalsubthree.ui.viewmodel.DetailViewModelFactory

class DetailActivity : AppCompatActivity() {

    private var _activityDetailBinding: ActivityDetailBinding? = null
    private val binding get() = _activityDetailBinding
    private var isFavorite = false

    private var favorite: Favorite = Favorite()
    private lateinit var detailViewModel: DetailViewModel

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_FAVORITE = "extra_favorite"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        detailViewModel = obtainViewModel(this@DetailActivity)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        binding?.viewPager?.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding?.tabs!!, binding?.viewPager!!) { tab, posistion ->
            tab.text = resources.getString(TAB_TITLES[posistion])
        }.attach()

        setFabFav(isFavorite)

        val responseDetail: ResponseDetail? = intent.getParcelableExtra(EXTRA_DATA)
        binding?.apply {
            tvItemName.text = responseDetail?.name ?: "-"
            tvItemLocation.text = responseDetail?.location ?: "="
            tvItemCompany.text = responseDetail?.company ?: "-"
            tvItemRepository.text = responseDetail?.publicRepos.toString()
            tvItemFollowers.text = responseDetail?.followers.toString()
            tvItemFollowing.text = responseDetail?.following.toString()
        }

        Glide.with(this)
            .load(responseDetail?.avatarUrl)
            .into(binding!!.imgItemPhoto)

        supportActionBar?.elevation = 0f
        supportActionBar?.title = responseDetail?.login

        numberFont()

        binding?.fabFav?.setOnClickListener{
            if (isFavorite){
                detailViewModel.delete(favorite as Favorite)
                setFabFav(false)
            }else{
                favorite?.let {
                    it.login = responseDetail?.login.toString()
                    it.name = responseDetail?.name.toString()
                    it.avatarUrl = responseDetail?.avatarUrl.toString()
                    it.location = responseDetail?.location.toString()
                    it.company = responseDetail?.company.toString()
                    it.publicRepos = responseDetail?.publicRepos.toString()
                    it.followers = responseDetail?.followers.toString()
                    it.following = responseDetail?.following.toString()
                }
                detailViewModel.insert(favorite as? Favorite)
                setFabFav(true)
            }

        }
    }

    private fun numberFont() {
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding?.apply {
                tvItemFollowers.textSize = 30F
                tvItemFollowing.textSize = 30F
                tvItemRepository.textSize = 30F
            }
        } else {
            binding?.apply {
                tvItemFollowers.textSize = 24F
                tvItemFollowing.textSize = 24F
                tvItemRepository.textSize = 24F
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel{
        val factory = DetailViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[DetailViewModel::class.java]
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityDetailBinding = null
    }

    private fun setFabFav(fav: Boolean){
        val fabIsFav = ContextCompat.getDrawable(this,R.drawable.ic_addfavorite)
        val fabNoFav = ContextCompat.getDrawable(this,R.drawable.ic_nofavorite)

        if (fav){
            binding?.fabFav?.setImageDrawable(fabIsFav)
        } else {
            binding?.fabFav?.setImageDrawable(fabNoFav)
        }
    }

}