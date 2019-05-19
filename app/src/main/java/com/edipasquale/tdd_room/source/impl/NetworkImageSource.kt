package com.edipasquale.tdd_room.source.impl

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.edipasquale.tdd_room.dto.Either
import com.edipasquale.tdd_room.dto.Image
import com.edipasquale.tdd_room.model.ERROR_DOWNLOADING_IMAGE
import com.edipasquale.tdd_room.source.ImageSource
import java.io.BufferedInputStream
import java.io.IOException
import java.net.URL

open class NetworkImageSource : ImageSource {

    override fun fetchImage(url: String): LiveData<Either<Image, Int>> {
        val liveData = MutableLiveData<Either<Image, Int>>()

        ImageAsyncTask(liveData).execute(url)

        return liveData
    }

    class ImageAsyncTask(private val liveData: MutableLiveData<Either<Image, Int>>) :
        AsyncTask<String, Void, Either<Image, Int>>() {

        override fun doInBackground(vararg urls: String?): Either<Image, Int> {
            val url = urls[0]

            val bm: Bitmap?
            try {
                val aURL = URL(url)
                val conn = aURL.openConnection()
                conn.connect()
                val inputStream = conn.getInputStream()
                val bis = BufferedInputStream(inputStream)
                bm = BitmapFactory.decodeStream(bis)
                bis.close()
                inputStream.close()
            } catch (e: IOException) {
                return Either.Error(ERROR_DOWNLOADING_IMAGE)
            }

            val result = Either.Data(Image(url = urls[0]!!))
            result.data.picture = bm

            return result
        }

        override fun onPostExecute(result: Either<Image, Int>) {
            super.onPostExecute(result)
            liveData.postValue(result)
        }
    }
}