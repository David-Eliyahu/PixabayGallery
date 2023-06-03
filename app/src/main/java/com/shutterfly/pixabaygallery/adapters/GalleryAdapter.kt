package com.shutterfly.pixabaygallery.adapters

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shutterfly.pixabaygallery.R
import com.shutterfly.pixabaygallery.models.network_models.GalleryItem

class GalleryAdapter : PagingDataAdapter<GalleryItem, GalleryViewHolder>(GalleryComparator) {

    object GalleryComparator : DiffUtil.ItemCallback<GalleryItem>() {

        override fun areItemsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean {
            return oldItem.previewUrl == newItem.previewUrl
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_image, parent, false)
        return GalleryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {

        if (position != RecyclerView.NO_POSITION) {

            getItem(position)?.let { galleryItem ->

                Glide.with(holder.itemView)
                    .load(galleryItem.previewUrl)
                    .placeholder(ColorDrawable(Color.LTGRAY))
                    .into(holder.imageView)
            }
        }
    }
}

class GalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val imageView: AppCompatImageView = itemView.findViewById(R.id.image)
}