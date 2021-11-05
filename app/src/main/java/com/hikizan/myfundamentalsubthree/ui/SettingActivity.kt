package com.hikizan.myfundamentalsubthree.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hikizan.myfundamentalsubthree.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    private var _activitySettingBinding : ActivitySettingBinding? = null
    private val binding get() = _activitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activitySettingBinding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding?.root)


    }
}