package com.example.cleanfloyd.presentation.albums

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanfloyd.R
import com.example.cleanfloyd.domain.model.Album
import kotlinx.android.synthetic.main.element_albums_list_item.view.*

internal class AlbumsAdapter() : RecyclerView.Adapter<AlbumsAdapter.ViewHolder>() {

  private val albums: MutableList<Album> = ArrayList()

  fun clear() {
    albums.clear()
    notifyDataSetChanged()
  }

  fun addData(list: List<Album>) {
    albums.addAll(list)
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(LayoutInflater.from(parent.context)
        .inflate(R.layout.element_albums_list_item, parent, false))
  }

  override fun getItemCount(): Int = albums.size

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(albums[position])
  }

  inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(album: Album) {
      Picasso
//      Picasso.get().load(offer.backgroundImageXhdpi).into(offer_image)
      itemView.title.text = album.title
    }
  }
}