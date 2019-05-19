package com.edipasquale.tdd_room.dto

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "images")
data class Image(var url: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    var imageUri: String? = null

    @Ignore
    var picture: Bitmap? = null
}
