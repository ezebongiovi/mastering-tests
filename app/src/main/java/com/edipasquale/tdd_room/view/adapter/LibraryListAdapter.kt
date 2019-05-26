package com.edipasquale.tdd_room.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.edipasquale.tdd_room.databinding.HolderImageBinding
import com.edipasquale.tdd_room.dto.Image
import com.edipasquale.tdd_room.view.adapter.holder.ImageViewHolder
import kotlinx.android.synthetic.main.holder_image.view.*

class LibraryListAdapter(val listener: ImageActionListener) : ListAdapter<Image, ImageViewHolder>(DiffCallback) {

    interface ImageActionListener {
        fun deleteImage(image: Image)

        fun shareImage(image: Image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ImageViewHolder(HolderImageBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.onBind(currentList[holder.adapterPosition])

        holder.itemView.actionDelete.setOnClickListener {
            listener.deleteImage(currentList[holder.adapterPosition])
        }

        holder.itemView.actionShare.setOnClickListener {
            listener.shareImage(currentList[holder.adapterPosition])
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Image, newItem: Image) = oldItem.url == newItem.url
    }
}