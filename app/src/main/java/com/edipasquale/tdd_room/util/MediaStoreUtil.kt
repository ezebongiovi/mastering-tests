package com.edipasquale.tdd_room.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.content.FileProvider
import com.edipasquale.tdd_room.dto.Image
import java.io.FileNotFoundException

const val IMAGE_EXTENSION = "jpeg"

class MediaStoreUtil private constructor() {

    init {
        throw AssertionError("Utility classes shouldn't be instantiated")
    }

    companion object {

        fun saveImageOnDisk(context: Context, image: Image): Boolean {
            if (image.picture == null)
                return false

            context.openFileOutput(getImageName(image), Context.MODE_PRIVATE).use {
                image.picture!!.compress(Bitmap.CompressFormat.JPEG, 100, it)
            }

            return true
        }

        fun getImageFromDisk(context: Context, image: Image): Bitmap? {
            try {
                context.openFileInput(getImageName(image)).use {
                    return BitmapFactory.decodeStream(it)
                }

            } catch (e: FileNotFoundException) {
                return null
            }
        }

        fun deleteImageFromDisk(context: Context, image: Image) {
            getImageFile(context, image).delete()
        }

        fun getImageFile(context: Context, image: Image) =
            context.getFileStreamPath(getImageName(image))

        fun getSharableImage(context: Context, image: Image): Uri {
            val file = getImageFile(context, image)
            return FileProvider.getUriForFile(
                context, "com.edipasquale.tdd_room.fileprovider",
                file
            )
        }

        private fun getImageName(image: Image) = "image${image.id}.${IMAGE_EXTENSION}"
    }
}