package com.edipasquale.tdd_room.view.adapter.holder

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.edipasquale.tdd_room.databinding.HolderImageBinding
import com.edipasquale.tdd_room.dto.Image

class ImageViewHolder(private val binding: HolderImageBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(image: Image) {
        binding.item = image
    }

    companion object {
        @BindingAdapter("holderImageBitmap")
        @JvmStatic
        fun setImageBitmap(imageView: ImageView, bitmap: Bitmap?) {
            if (bitmap != null)
                imageView.setImageBitmap(bitmap)
        }
    }
}