package com.gematriga.postaff.ViewModelFactory

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gematriga.postaff.Models.WallpaperResponse
import com.gematriga.postaff.Repository.WallpaperRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import kotlin.random.Random

class ViewModel(val repository: WallpaperRepository) : ViewModel() {

    val concepts = arrayOf("ocean","tech","sea","sunset","city","view","mountain","people","activity","car","music","game","computer","spring","winter","summer","fall","snow","rain","cloud","tree","lake")
    val randomIndex = Random.nextInt(concepts.size)
    val randomElement = concepts[randomIndex]

    var wallpaperList : MutableLiveData<Response<WallpaperResponse>>

    init{
        wallpaperList = MutableLiveData()
        getWallpaper(randomElement)
    }

    fun getWallpaper(s: String){

        viewModelScope.launch {

            val response = repository.getWallpaper(s)

            wallpaperList.postValue(response)

        }

    }
}