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

    private var favorite: Favorite? = null
    private var responseDetail: ResponseDetail? = null
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

        favorite = intent.getParcelableExtra(EXTRA_FAVORITE)
        Log.d("DetailActivity", "onCreate: favorite getParcelable : $favorite")
        //responseDetail = intent.getParcelableExtra(EXTRA_DATA)

        checkToSetDataDetail(favorite)

        supportActionBar?.elevation = 0f

        numberFont()

        binding?.fabFav?.setOnClickListener {
            if (isFavorite){
                detailViewModel.delete(favorite as Favorite)
                setFabFav(false)
            }
            /*
            else if (isFavorite && responseDetail != null) {
                //detailViewModel.deleteWithUsername(getUsername)

                detailViewModel.getFavoriteUser(responseDetail?.login).observe(this,{
                  favUser = it
                })
                detailViewModel.delete(favUser)
                setFabFav(false)
            }
             */
            else {
                favorite = Favorite()
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
                detailViewModel.insert(favorite as Favorite)
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

    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel {
        val factory = DetailViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[DetailViewModel::class.java]
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

    private fun checkToSetDataDetail(favorite: Favorite?) {
        if (favorite != null) {
            Log.d("DetailActivity", "onCreate: on IF(favorite != null) statement")
            isFavorite = true
            setFabFav(true)
            supportActionBar?.title = favorite.login
            binding?.tvItemName?.text = favorite.name ?: "-"
            binding?.tvItemLocation?.text = favorite.location ?: "-"
            binding?.tvItemCompany?.text = favorite.company ?: "-"
            binding?.tvItemRepository?.text = favorite.publicRepos.toString()
            binding?.tvItemFollowers?.text = favorite.followers.toString()
            binding?.tvItemFollowing?.text = favorite.following.toString()

            Glide.with(this)
                .load(favorite.avatarUrl)
                .into(binding!!.imgItemPhoto)

        } else {
            Log.d("DetailActivity", "onCreate: on ELSE IF(favorite == null) statement")
            responseDetail = intent.getParcelableExtra(EXTRA_DATA)

            if (detailViewModel.detailUserFavorited(responseDetail?.login) != null){
                Log.d("DetailActivity", "checkToSetDataDetail: jika ini jalan maka user berada di favorite")
                isFavorite = true
                setFabFav(true)
            }else{
                Log.d("DetailActivity", "checkToSetDataDetail: jika ini jalan maka user tidak ada di favorite")
                isFavorite = false
                setFabFav(false)
            }
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

            supportActionBar?.title = responseDetail?.login

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityDetailBinding = null
        favorite = null
        responseDetail = null
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