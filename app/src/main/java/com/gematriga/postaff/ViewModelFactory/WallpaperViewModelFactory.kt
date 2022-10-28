package com.gematriga.postaff.ViewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gematriga.postaff.Repository.WallpaperRepository

class WallpaperViewModelFactory(private val wallpaperRepository: WallpaperRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ViewModel(wallpaperRepository) as T
    }
}