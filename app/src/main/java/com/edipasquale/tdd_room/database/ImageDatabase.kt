package com.edipasquale.tdd_room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.edipasquale.tdd_room.dto.Image

@Database(entities = [Image::class], version = 2)
abstract class ImageDatabase : RoomDatabase() {
    abstract fun getImageDao(): ImageDao
}