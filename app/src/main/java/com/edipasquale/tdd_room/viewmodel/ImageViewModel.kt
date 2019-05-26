package com.edipasquale.tdd_room.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.edipasquale.tdd_room.dto.Either
import com.edipasquale.tdd_room.dto.Image
import com.edipasquale.tdd_room.model.ImageLibraryErrorModel
import com.edipasquale.tdd_room.model.ImageLibraryModel
import com.edipasquale.tdd_room.model.ImageModel
import com.edipasquale.tdd_room.repository.ImageRepository

class ImageViewModel(private val repository: ImageRepository) : ViewModel() {
    private var imageLiveData: LiveData<ImageModel>? = null
    private var libraryLiveData: LiveData<Either<ImageLibraryModel, ImageLibraryErrorModel>>? = null

    val model = MediatorLiveData<ImageModel>()
    val error = MediatorLiveData<ImageModel>()
    val errorLibraryModel = MediatorLiveData<ImageLibraryErrorModel>()
    val libraryModel = MediatorLiveData<ImageLibraryModel>()

    fun downloadImage(url: String) {
        addSource(repository.getImage(url))
    }

    private fun addSource(liveData: LiveData<ImageModel>) {
        if (imageLiveData != null || imageLiveData?.value != null) {
            model.removeSource(imageLiveData!!)
        }

        imageLiveData = liveData
        model.addSource(imageLiveData!!) { response ->
            if (response.isSuccess()) {
                model.postValue(response)
                getLibrary(null)
            } else
                error.postValue(response)
        }
    }

    fun getLibrary(query: String?) {
        addLibrarySource(repository.getLibrary(query))
    }

    private fun addLibrarySource(liveData: LiveData<Either<ImageLibraryModel, ImageLibraryErrorModel>>) {
        if (libraryLiveData != null || libraryLiveData?.value != null) {
            libraryModel.removeSource(libraryLiveData!!)
        }

        libraryLiveData = liveData
        libraryModel.addSource(libraryLiveData!!) { response ->
            when (response) {
                is Either.Data -> libraryModel.postValue(response.data)
                is Either.Error -> errorLibraryModel.postValue(response.error)
            }
        }
    }

    fun deleteFromLibrary(image: Image) {
        addLibrarySource(repository.deleteImage(image))
    }
}