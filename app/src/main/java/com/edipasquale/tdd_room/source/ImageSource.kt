package com.edipasquale.tdd_room.source

import androidx.lifecycle.LiveData
import com.edipasquale.tdd_room.dto.Either
import com.edipasquale.tdd_room.dto.Image

interface ImageSource {
    fun fetchImage(url: String): LiveData<Either<Image, Int>>
}