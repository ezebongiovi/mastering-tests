package com.edipasquale.tdd_room.database

import androidx.room.*
import com.edipasquale.tdd_room.dto.Image

@Dao
interface ImageDao {
    @Insert
    fun insertImage(image: Image): Long

    @Query("SELECT * FROM images WHERE url LIKE :url")
    fun getImage(url: String): Image?

    @Delete
    fun deleteImage(image: Image)

    @Query("SELECT * FROM images")
    fun getAllImages(): List<Image>

    @Query("SELECT * FROM images WHERE id LIKE :query")
    fun getImages(query: String): List<Image>

    @Update
    fun updateImage(image: Image)
}