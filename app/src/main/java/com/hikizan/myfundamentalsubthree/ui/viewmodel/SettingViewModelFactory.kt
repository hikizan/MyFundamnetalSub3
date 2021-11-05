package com.hikizan.myfundamentalsubthree.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hikizan.myfundamentalsubthree.preference.SettingPreferences
import java.lang.IllegalArgumentException

class SettingViewModelFactory(private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)){
            return SettingViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}