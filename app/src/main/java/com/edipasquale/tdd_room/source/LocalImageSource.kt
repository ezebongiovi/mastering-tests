package com.edipasquale.tdd_room.source

import androidx.lifecycle.LiveData
import com.edipasquale.tdd_room.dto.Either
import com.edipasquale.tdd_room.dto.Image

/**
 * Local source is intended to save and fetch data from the device, without requiring internet
 * connection.
 */
interface LocalImageSource {

    /**
     * Fetch the image from a local source
     */
    fun fetchImage(url: String): LiveData<Either<Image, Int>>

    /**
     * Save the image into a local source
     */
    fun saveImage(image: Image): LiveData<Either<Image, Int>>

    /**
     * Fetch all the images saved on the local source
     */
    fun getLibrary(query: String?): LiveData<Either<List<Image>, Int>>

    /**
     * Delete an image from the local source
     */
    fun deleteImage(image: Image): LiveData<Either<List<Image>, Int>>
}