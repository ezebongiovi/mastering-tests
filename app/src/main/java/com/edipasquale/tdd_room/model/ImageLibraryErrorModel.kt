package com.edipasquale.tdd_room.model

import android.content.Context

data class ImageLibraryErrorModel(val errorCode: Int) {
    fun getErrorMessage(context: Context): String = "Empty for now"
}