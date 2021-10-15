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
    ArrayAdapter<Album>(context, 0) {

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
            convertView = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_albums_list_item, parent, false)
        }
        val album: Album? = getItem(position)

        // bind the data to views
        val txtTitle = convertView!!.findViewById<TextView>(R.id.txt_title)
        txtTitle.text = album?.title

        // set listener
        convertView.setOnClickListener {
            if (album != null) {
                onAlbumClicked(album)
            }
        }
        Log.d(TAG, "getView: $convertView")
        return convertView
    }

    private fun onAlbumClicked(album: Album) {
        Log.d(TAG, "onAlbumClicked: $album")
        onAlbumClickListener.onAlbumClicked(album)
    }
}