package com.edipasquale.tdd_room.model

import android.content.Context
import com.edipasquale.tdd_room.R
import com.edipasquale.tdd_room.dto.Image

const val ERROR_DOWNLOADING_IMAGE = 400
const val ERROR_NOT_FOUND = 404
const val ERROR_INTERNAL_STORAGE = 500

data class ImageModel(val image: Image? = null, val error: Int? = null) {

    fun isSuccess() = image != null

    fun getErrorMessage(context: Context): String? {
        return when (error) {
            ERROR_NOT_FOUND -> context.getString(R.string.error_not_found)
            ERROR_DOWNLOADING_IMAGE -> context.getString(R.string.error_image_download)
            ERROR_INTERNAL_STORAGE -> context.getString(R.string.error_image_internal_storage)
            else -> null
        }
    }
}