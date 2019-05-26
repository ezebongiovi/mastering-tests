package com.edipasquale.tdd_room.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.edipasquale.tdd_room.repository.ImageRepository
import com.edipasquale.tdd_room.viewmodel.ImageViewModel

class ImageViewModelFactory(val repository: ImageRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = ImageViewModel(repository) as T
}