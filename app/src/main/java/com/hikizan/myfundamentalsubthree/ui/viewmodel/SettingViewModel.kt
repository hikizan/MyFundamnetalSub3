package com.hikizan.myfundamentalsubthree.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.hikizan.myfundamentalsubthree.preference.SettingPreferences
import kotlinx.coroutines.launch

class SettingViewModel(private val pref: SettingPreferences) : ViewModel() {

    fun getThemeSetting(): LiveData<Boolean>{
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean){
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }

}