package com.layon.mvc.screens.albumlist

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.layon.mvc.R
import com.layon.mvc.albums.Album
import java.util.*

class AlbumListViewMvcImpl(inflater : LayoutInflater, parent : ViewGroup?) : AlbumListViewMvc, AlbumsListAdapter.OnAlbumClickListener {

    private val TAG = "layon.f - AlbumListViewMvcImpl"
    private var mRootView : View = inflater.inflate(R.layout.layout_albums_list, parent, false)
    private var mLstAlbums: ListView = findViewById(R.id.lst_albums)
    private var mAlbumsListAdapter : AlbumsListAdapter = AlbumsListAdapter(getContext(), this)
    private var mListeners: MutableList<AlbumListViewMvc.Listener> = ArrayList<AlbumListViewMvc.Listener>(1)

    init {
        Log.d(TAG, "init: mLstAlbums.adapter = mAlbumsListAdapter")
        mLstAlbums.adapter = mAlbumsListAdapter
    }

    override fun registerListener(listener: AlbumListViewMvc.Listener) {
        mListeners.add(listener)
    }

    override fun unregisterListener(listener: AlbumListViewMvc.Listener) {
        mListeners.remove(listener)
    }

    override fun getRootView(): View {
        return mRootView
    }

    override fun bindAlbums(albuns: List<Album>) {
        Log.d(TAG, "bindAlbums: $albuns")
        mAlbumsListAdapter.apply{
            clear()
            addAll(albuns)
            notifyDataSetChanged()
        }
    }

    override fun onAlbumClicked(album: Album) {
        mListeners.forEach{
            Log.d(TAG, "onAlbumClicked: $album")
            it.onAlbumClicked(album)
        }
    }

    private fun getContext() : Context {
        return getRootView().context
    }

    private fun <T : View?> findViewById(id: Int): T {
        return getRootView().findViewById(id)
    }
}