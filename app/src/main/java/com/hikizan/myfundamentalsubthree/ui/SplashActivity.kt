package com.hikizan.myfundamentalsubthree.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.hikizan.myfundamentalsubthree.databinding.ActivitySplashBinding
import com.hikizan.myfundamentalsubthree.preference.SettingPreferences
import com.hikizan.myfundamentalsubthree.ui.viewmodel.SettingViewModel
import com.hikizan.myfundamentalsubthree.ui.viewmodel.SettingViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private var delay = 2000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(dataStore)
        val settingViewModel = ViewModelProvider(this, SettingViewModelFactory(pref)).get(
            SettingViewModel::class.java
        )
        setTheme(settingViewModel)

        Handler(Looper.getMainLooper()).postDelayed({
            val goHome = Intent(this, MainActivity::class.java)
            startActivity(goHome)
            finish()
        }, delay)
    }

    private fun setTheme(viewModel: SettingViewModel) {
        viewModel.getThemeSetting().observe(this,
            { isDarkModeActive ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        )
    }
}