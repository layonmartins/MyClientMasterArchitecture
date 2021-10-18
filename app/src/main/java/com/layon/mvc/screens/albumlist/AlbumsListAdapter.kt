package com.layon.mvc.screens.albumlist

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.layon.mvc.R
import com.layon.mvc.albums.Album

class AlbumsListAdapter(context: Context, var onAlbumClickListener: OnAlbumClickListener) :
    ArrayAdapter<Album>(context, 0), AlbumListViewMvc.Listener {

    private val TAG = "layon.f - AlbumsListAdapter"

    interface OnAlbumClickListener {
        fun onAlbumClicked(album: Album)
    }

    @NonNull
    override fun getView(
        position: Int,
        @Nullable convertView: View?,
        @NonNull parent: ViewGroup
    ): View {
        var convertView = convertView
        if (convertView == null) {
            var viewMvc = AlbumListItemViewMvcImpl(LayoutInflater.from(context), parent)
            viewMvc.registerListener(this)
            convertView = viewMvc.getRootView()
            convertView.tag = viewMvc
        }

        val album: Album? = getItem(position)
        var viewMvc = convertView?.tag as AlbumListItemViewMvc
        viewMvc.bindAlbum(album!!)

        return convertView
    }

    override fun onAlbumClicked(album: Album) {
        Log.d(TAG, "onAlbumClicked: $album")
        onAlbumClickListener.onAlbumClicked(album)
    }
}