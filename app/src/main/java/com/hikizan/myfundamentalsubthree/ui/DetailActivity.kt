package com.hikizan.myfundamentalsubthree.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.hikizan.myfundamentalsubthree.R
import com.hikizan.myfundamentalsubthree.adapter.SectionsPagerAdapter
import com.hikizan.myfundamentalsubthree.databinding.ActivityDetailBinding
import com.hikizan.myfundamentalsubthree.model.detail.ResponseDetail

class DetailActivity : AppCompatActivity() {

    private var _activityDetailBinding: ActivityDetailBinding? = null
    private val binding get() = _activityDetailBinding

    companion object {
        const val EXTRA_DATA = "extra_data"

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

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        binding?.viewPager?.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding?.tabs!!, binding?.viewPager!!) { tab, posistion ->
            tab.text = resources.getString(TAB_TITLES[posistion])
        }.attach()


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

    override fun onDestroy() {
        super.onDestroy()
        _activityDetailBinding = null
    }
}