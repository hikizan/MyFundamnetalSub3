package com.hikizan.myfundamentalsubtwo.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.hikizan.myfundamentalsubtwo.R
import com.hikizan.myfundamentalsubtwo.adapter.SectionsPagerAdapter
import com.hikizan.myfundamentalsubtwo.databinding.ActivityDetailBinding
import com.hikizan.myfundamentalsubtwo.model.detail.ResponseDetail

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

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
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, posistion ->
            tab.text = resources.getString(TAB_TITLES[posistion])
        }.attach()


        val responseDetail: ResponseDetail? = intent.getParcelableExtra(EXTRA_DATA)
        binding.apply {
            tvItemName.text = responseDetail?.name ?: "-"
            tvItemLocation.text = responseDetail?.location ?: "="
            tvItemCompany.text = responseDetail?.company ?: "-"
            tvItemRepository.text = responseDetail?.publicRepos.toString()
            tvItemFollowers.text = responseDetail?.followers.toString()
            tvItemFollowing.text = responseDetail?.following.toString()
        }

        Glide.with(this)
            .load(responseDetail?.avatarUrl)
            .into(binding.imgItemPhoto)

        supportActionBar?.elevation = 0f
        supportActionBar?.title = responseDetail?.login

        numberFont()
    }

    private fun numberFont() {
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.tvItemFollowers.textSize = 30F
            binding.tvItemFollowing.textSize = 30F
            binding.tvItemRepository.textSize = 30F

        } else {
            binding.tvItemFollowers.textSize = 24F
            binding.tvItemFollowing.textSize = 24F
            binding.tvItemRepository.textSize = 24F
        }
    }
}