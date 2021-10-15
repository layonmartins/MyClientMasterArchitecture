package com.layon.mvc.screens.albumlist

import android.view.View
import com.layon.mvc.albums.Album

interface AlbumListViewMvc {

    interface Listener{
        fun onAlbumClicked(album : Album)
    }
    fun registerListener(listener : Listener)
    fun unregisterListener(listener : Listener)
    fun getRootView(): View
    fun bindAlbums(albums : List<Album>)
}