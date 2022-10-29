package com.gematriga.postaff.Api

import com.gematriga.postaff.Models.WallpaperResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiInterface {

    @Headers("Authorization: 563492ad6f91700001000001327dcd733c9d41b28135f1e8339b5603")
    @GET("search")
    suspend fun getWallpaper(
        @Query("query") query: String = "nature",
        @Query("per_page") perpage : Int = 80

    ): Response<WallpaperResponse>

}

