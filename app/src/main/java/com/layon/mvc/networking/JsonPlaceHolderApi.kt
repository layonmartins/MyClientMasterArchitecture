package com.layon.mvc.networking

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface JsonPlaceHolderApi {

    @GET("/albums")
    fun getAlbums(@Query("_limit") limit: Int): Call<List<AlbumSchema>>
}