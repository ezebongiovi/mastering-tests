package com.edipasquale.tdd_room.source.impl

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.edipasquale.tdd_room.database.ImageDao
import com.edipasquale.tdd_room.dto.Either
import com.edipasquale.tdd_room.dto.Image
import com.edipasquale.tdd_room.model.ERROR_INTERNAL_STORAGE
import com.edipasquale.tdd_room.model.ERROR_NOT_FOUND
import com.edipasquale.tdd_room.model.ImageModel
import com.edipasquale.tdd_room.source.ImageSource
import com.edipasquale.tdd_room.util.MediaStoreUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


open class LocalImageSource(private val imageDao: ImageDao, private val context: Context) : ImageSource {

    override fun fetchImage(url: String): LiveData<Either<Image, Int>> {
        val liveData = MutableLiveData<Either<Image, Int>>()

        GlobalScope.launch {
            liveData.postValue(handleResponse(imageDao.getImage(url)))
        }

        return liveData
    }

    fun saveImage(image: Image): LiveData<ImageModel> {
        val liveData = MutableLiveData<ImageModel>()

        GlobalScope.launch {
            // Saves on database and gets the ID
            image.apply {
                id = imageDao.insertImage(image)
            }

            // Saves image on disk
            if (MediaStoreUtil.saveImageOnDisk(context, image)) {

                // Get's the saved image path
                val uri = MediaStoreUtil.getImageFile(context, image).absolutePath

                // Builds the updated image object
                val updatedImage = Image(image.url).apply {
                    picture = image.picture
                    id = image.id
                    imageUri = uri
                }

                // Updates the image on DB
                imageDao.updateImage(image)

                // Returns the updated image
                liveData.postValue(ImageModel(updatedImage))
            } else {
                liveData.postValue(ImageModel(error = ERROR_INTERNAL_STORAGE))
            }
        }

        return liveData
    }

    private fun handleResponse(response: Image?): Either<Image, Int> {
        return if (response == null)
            Either.Error(ERROR_NOT_FOUND)
        else {
            response.picture = MediaStoreUtil.getImageFromDisk(context, response)
            response.imageUri = MediaStoreUtil.getImageFile(context, response).absolutePath

            Either.Data(response)
        }
    }

    fun getLibrary(query: String?): LiveData<Either<List<Image>, Int>> {
        val liveData = MutableLiveData<Either<List<Image>, Int>>()

        GlobalScope.launch {
            val data = if (query == null || query.isEmpty())
                imageDao.getAllImages()
            else
                imageDao.getImages(query)

            data.forEach { image ->
                image.apply {
                    picture = MediaStoreUtil.getImageFromDisk(context, image)
                }
            }

            data.filter { image ->
                image.imageUri != null
            }

            liveData.postValue(Either.Data(data))
        }

        return liveData
    }

    fun deleteImage(image: Image): LiveData<Either<List<Image>, Int>> {
        val liveData = MutableLiveData<Either<List<Image>, Int>>()

        GlobalScope.launch {
            imageDao.deleteImage(image)

            MediaStoreUtil.deleteImageFromDisk(context, image)

            liveData.postValue(Either.Data(imageDao.getAllImages()))
        }

        return liveData
    }
}