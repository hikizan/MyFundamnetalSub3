package com.hikizan.myfundamentalsubthree.ui

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
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

    private var fromFavorite: Favorite? = null
    private var responseDetail: ResponseDetail? = null
    private var tempFavorite: Favorite? = null
    private var fromRes: Favorite? = null
    private lateinit var detailViewModel: DetailViewModel


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

        fromFavorite = intent.getParcelableExtra(EXTRA_FAVORITE)
        responseDetail = intent.getParcelableExtra(EXTRA_DATA)

        setDataOnDetail(fromFavorite, responseDetail)

        checkFavorite(fromFavorite, responseDetail)

        supportActionBar?.elevation = 0f

        numberFont()

        binding?.fabFav?.setOnClickListener {
            if (isFavorite) {

                if (fromRes != null) {
                    detailViewModel.dataToDelete(tempFavorite?.login.toString())
                } else {
                    detailViewModel.delete(tempFavorite as Favorite)
                }

            } else {

                detailViewModel.insert(tempFavorite)

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

    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel {
        val factory = DetailViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[DetailViewModel::class.java]
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityDetailBinding = null
        fromFavorite = null
        responseDetail = null
    }

    private fun setDataOnDetail(fromFavorite: Favorite?, responseDetail: ResponseDetail?) {
        if (responseDetail != null) {
            binding?.apply {
                tvItemName.text = responseDetail.name ?: "-"
                tvItemLocation.text = responseDetail.location ?: "-"
                tvItemCompany.text = responseDetail.company ?: "-"
                tvItemRepository.text = responseDetail.publicRepos.toString()
                tvItemFollowers.text = responseDetail.followers.toString()
                tvItemFollowing.text = responseDetail.following.toString()
            }

            Glide.with(this)
                .load(responseDetail.avatarUrl)
                .into(binding!!.imgItemPhoto)

            supportActionBar?.title = responseDetail.login
        } else {
            binding?.apply {
                tvItemName.text = fromFavorite?.name ?: "-"
                tvItemLocation.text = fromFavorite?.location ?: "-"
                tvItemCompany.text = fromFavorite?.company ?: "-"
                tvItemRepository.text = fromFavorite?.publicRepos.toString()
                tvItemFollowers.text = fromFavorite?.followers.toString()
                tvItemFollowing.text = fromFavorite?.following.toString()
            }

            Glide.with(this)
                .load(fromFavorite?.avatarUrl)
                .into(binding!!.imgItemPhoto)
        }
    }

    private fun checkFavorite(fromFavorite: Favorite?, responseDetail: ResponseDetail?) {

        when {

            responseDetail != null -> {
                fromRes = Favorite()

                fromRes?.let {
                    it.login = responseDetail.login.toString()
                    it.name = responseDetail.name.toString()
                    it.avatarUrl = responseDetail.avatarUrl.toString()
                    it.location = responseDetail.location.toString()
                    it.company = responseDetail.company.toString()
                    it.publicRepos = responseDetail.publicRepos.toString()
                    it.followers = responseDetail.followers.toString()
                    it.following = responseDetail.following.toString()
                }

                when {
                    fromRes?.name == null -> {
                        fromRes?.name = "-"
                    }
                    fromRes?.location == null -> {
                        fromRes?.location = "-"
                    }
                    fromRes?.company == null -> {
                        fromRes?.company = "-"
                    }
                }
            }

            fromFavorite != null -> tempFavorite = fromFavorite

        }

        if (fromRes != null) {
            tempFavorite = fromRes
        }

        detailViewModel.findSpecificUser(tempFavorite?.login)?.observe(this, { findUser ->
            Log.d("DetailActivity_checkFav", "checkFavorite: findUser = $findUser")
            if (findUser == null) {
                isFavorite = false
                setFabFav(isFavorite)
            } else {
                isFavorite = true
                setFabFav(isFavorite)
            }
            Log.d("DetailActivity_checkFav", "checkFavorite: isFavorite = $isFavorite")
        })

    }

    private fun setFabFav(fav: Boolean) {
        val fabIsFav = ContextCompat.getDrawable(this, R.drawable.ic_addfavorite)
        val fabNoFav = ContextCompat.getDrawable(this, R.drawable.ic_nofavorite)

        if (fav) {
            binding?.fabFav?.setImageDrawable(fabIsFav)
        } else {
            binding?.fabFav?.setImageDrawable(fabNoFav)
        }
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_FAVORITE = "extra_favorite"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

}