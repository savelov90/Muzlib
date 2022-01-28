package com.example.muzlib.view.rv_viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.muzlib.R
import com.example.muzlib.data.db.entity_search.ResultAlbums
import com.example.muzlib.databinding.AlbumItemBinding
import com.squareup.picasso.Picasso


//В конструктор класс передается layout (album_item.xml)
class AlbumsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val albumItemBinding = AlbumItemBinding.bind(itemView)
    private val name = albumItemBinding.name
    private val artist = albumItemBinding.artist
    private val date = albumItemBinding.date
    private val picture = albumItemBinding.picture
    private val style = albumItemBinding.style

    fun bind(resultAlbums: ResultAlbums) {
        name.text = resultAlbums.collectionName
        artist.text = resultAlbums.artistName
        date.text = editData(resultAlbums.releaseDate)
        style.text = resultAlbums.primaryGenreName

        if (resultAlbums.artworkUrl100.isEmpty()) {
            picture.setImageResource(R.drawable.white)
        } else {
            Picasso.get()
                .load(resultAlbums.artworkUrl100)
                .error(android.R.drawable.stat_notify_error)
                .into(picture)
        }
    }

    private fun editData(data: String): String {
        return data.dropLast(16)
    }
}