package com.layon.mvc.screens.albumlist

import android.view.View
import com.layon.mvc.albums.Album

interface AlbumListItemViewMvc {

    interface Listener{
        fun onAlbumClicked(album : Album)
    }

    fun getRootView(): View
    fun registerListener(listener : AlbumListViewMvc.Listener)
    fun unregisterListener(listener : AlbumListViewMvc.Listener)
    fun bindAlbum(album : Album)
}