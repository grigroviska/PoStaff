package com.gematriga.postaff.Api

import com.gematriga.postaff.Models.WallpaperResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiInterface {

    @Headers("Authorization: 563492ad6f91700001000001970e6f7e67324e8baa55aa781874572b")
    @GET("search")
    suspend fun getWallpaper(
        @Query("query") query: String = "nature",
        @Query("per_page") perpage : Int = 80

    ): Response<WallpaperResponse>

}