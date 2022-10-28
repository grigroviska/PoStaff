package com.gematriga.postaff.Repository

import com.gematriga.postaff.Api.RetrofitInstance

class WallpaperRepository {

    suspend fun getWallpaper(query: String) = RetrofitInstance.api.getWallpaper(query)

}