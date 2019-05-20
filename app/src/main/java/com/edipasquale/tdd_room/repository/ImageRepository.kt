package com.edipasquale.tdd_room.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.edipasquale.tdd_room.dto.Either
import com.edipasquale.tdd_room.dto.Image
import com.edipasquale.tdd_room.model.ImageLibraryErrorModel
import com.edipasquale.tdd_room.model.ImageLibraryModel
import com.edipasquale.tdd_room.model.ImageModel
import com.edipasquale.tdd_room.source.impl.LocalImageSource
import com.edipasquale.tdd_room.source.impl.NetworkImageSource

open class ImageRepository(
    private val localSource: LocalImageSource,
    private val source: NetworkImageSource
) {

    fun getImage(url: String): LiveData<ImageModel> = Transformations.switchMap(
        localSource.fetchImage(url)
    ) { response ->
        when (response) {
            is Either.Data -> {
                val liveData = MutableLiveData<ImageModel>()

                liveData.postValue(ImageModel(Image(url).apply {
                    picture = response.data.picture
                }))

                liveData
            }

            is Either.Error -> Transformations.switchMap(source.fetchImage(url)) { networkResponse ->

                when (networkResponse) {
                    is Either.Data -> {
                        localSource.saveImage(networkResponse.data)
                    }

                    is Either.Error -> {
                        val liveData = MutableLiveData<ImageModel>()

                        liveData.postValue(ImageModel(error = networkResponse.error))

                        liveData
                    }
                }

            }
        }
    }

    fun getLibrary(query: String?): LiveData<Either<ImageLibraryModel, ImageLibraryErrorModel>> =
        Transformations.map(localSource.getLibrary(query)) { response ->
            when (response) {
                is Either.Data -> Either.Data(ImageLibraryModel(response.data))
                is Either.Error -> Either.Error(ImageLibraryErrorModel(response.error))
            }
        }

    fun deleteImage(image: Image): LiveData<Either<ImageLibraryModel, ImageLibraryErrorModel>> =
        Transformations.map(localSource.deleteImage(image)) { response ->
            when (response) {
                is Either.Data -> Either.Data(ImageLibraryModel(response.data))
                is Either.Error -> Either.Error(ImageLibraryErrorModel(response.error))
            }
        }
}