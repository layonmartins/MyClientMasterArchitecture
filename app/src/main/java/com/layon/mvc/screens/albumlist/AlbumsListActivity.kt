package com.layon.mvc.screens.albumlist

import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import com.layon.mvc.R
import com.layon.mvc.albums.Album
import com.layon.mvc.common.Constants
import com.layon.mvc.networking.AlbumSchema
import com.layon.mvc.networking.JsonPlaceHolderApi
import com.layon.mvc.screens.common.BaseActivity
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AlbumsListActivity : BaseActivity(), AlbumsListAdapter.OnAlbumClickListener {

    private lateinit var jsonPlaceHolderApi: JsonPlaceHolderApi
    private lateinit var listAlbum: ListView
    private lateinit var albumsListAdapter: AlbumsListAdapter
    private val TAG = "layon.f - AlbumsListActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_albums_list)

        listAlbum = findViewById(R.id.lst_albums)
        albumsListAdapter = AlbumsListAdapter(this, this)
        listAlbum.adapter = albumsListAdapter
        jsonPlaceHolderApi = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(setupLog())
            .build()
            .create(JsonPlaceHolderApi::class.java)
    }

    // This method setup "OkHttp" log for retrofit operations
    private fun setupLog(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        return httpClient.build()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
        fetchAlbums()
    }

    private fun fetchAlbums() {
        Log.d(TAG, "fetchAlbums")
        jsonPlaceHolderApi.getAlbums(Constants.LIMIT).enqueue(
            object : Callback<List<AlbumSchema>> {

                override fun onResponse(
                    call: Call<List<AlbumSchema>>,
                    response: Response<List<AlbumSchema>>
                ) {
                    if (response.isSuccessful) {
                        Log.d(TAG, "fetchAlbums.response.isSuccessful: ${response.body()}")
                        bindAlbums(response.body()!!)
                    } else {
                        Log.d(TAG, "fetchAlbums.response.error")
                        networkCallFailed()
                    }
                }

                override fun onFailure(call: Call<List<AlbumSchema>>, t: Throwable) {
                    Log.d(TAG, "fetchAlbums.onFailure: ${t.message}")
                    networkCallFailed()
                }
            })
    }


    private fun bindAlbums(albumSchema: List<AlbumSchema>) {
        Log.d(TAG, "bindAlbums")

        var albumList: MutableList<Album> = arrayListOf()
        albumSchema.forEach{
            albumList.add(Album(it.userId, it.id, it.title))
        }

        albumsListAdapter.clear()
        albumsListAdapter.addAll(albumList)
        albumsListAdapter.notifyDataSetChanged()
    }

    private fun networkCallFailed() {
        Toast.makeText(this, R.string.error_network_call_failed, Toast.LENGTH_SHORT).show()
    }

    override fun onAlbumClicked(album: Album?) {
        Toast.makeText(this, album?.title, Toast.LENGTH_SHORT).show()
    }

}