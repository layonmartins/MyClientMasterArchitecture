package com.layon.mvc.screens.albumlist

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.layon.mvc.R
import com.layon.mvc.albums.Album
import java.util.ArrayList

class AlbumListItemViewMvcImpl(inflater : LayoutInflater, parent : ViewGroup?) : AlbumListItemViewMvc {

    private val TAG = "layon.f - AlbumListItemViewMvcImpl"
    private var mRootView : View = inflater.inflate(R.layout.layout_albums_list_item, parent, false)
    private var mTxtTitle : TextView = findViewById(R.id.txt_title)
    private var mListeners: MutableList<AlbumListViewMvc.Listener> = ArrayList<AlbumListViewMvc.Listener>(1)
    private lateinit var mAlbum : Album

    init {
        Log.d(TAG, "init")
        getRootView().setOnClickListener{
            mListeners.forEach{
                Log.d(TAG, "onAlbumClicked:")
                it.onAlbumClicked(mAlbum)
            }
        }
    }

    override fun getRootView(): View {
        return mRootView
    }

    override fun registerListener(listener: AlbumListViewMvc.Listener) {
        mListeners.add(listener)
    }

    override fun unregisterListener(listener: AlbumListViewMvc.Listener) {
        mListeners.remove(listener)
    }

    override fun bindAlbum(album: Album) {
        Log.d(TAG, "bindAlbum: $album")
        mAlbum = album
        mTxtTitle.text = album.title
    }

    private fun <T : View?> findViewById(id: Int): T {
        return getRootView().findViewById(id)
    }
}