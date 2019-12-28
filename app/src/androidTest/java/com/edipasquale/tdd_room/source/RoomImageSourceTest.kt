package com.edipasquale.tdd_room.source

import android.graphics.Bitmap
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.edipasquale.tdd_room.database.ImageDao
import com.edipasquale.tdd_room.database.ImageDatabase
import com.edipasquale.tdd_room.dto.Either
import com.edipasquale.tdd_room.dto.Image
import com.edipasquale.tdd_room.model.ERROR_NOT_FOUND
import com.edipasquale.tdd_room.observer.OneTimeObserver
import com.edipasquale.tdd_room.source.impl.RoomImageSource
import com.edipasquale.tdd_room.util.MediaStoreUtil
import junit.framework.AssertionFailedError
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RoomImageSourceTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var dao: ImageDao
    private lateinit var source: RoomImageSource

    @Before
    fun setup() {
        dao = Room.inMemoryDatabaseBuilder<ImageDatabase>(
            ApplicationProvider.getApplicationContext(),
            ImageDatabase::class.java
        ).build().getImageDao()

        source = RoomImageSource(dao, ApplicationProvider.getApplicationContext())
    }

    @Test
    fun testLocalSuccess() {
        val image = Image("success")

        dao.insertImage(image)

        source.fetchImage(image.url).observeOnce { response ->
            assertTrue(response is Either.Data)
        }
    }

    @Test
    fun testLocalError() {
        source.fetchImage("error").observeOnce { response ->
            assertTrue(response is Either.Error)

            assertTrue((response as Either.Error).error == ERROR_NOT_FOUND)
        }
    }

    @Test
    fun testInsertAndFileCreation() {
        val image = Image("insert").apply {
            picture = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
        }

        // Tells the source to save the image
        source.saveImage(image).observeOnce { saveResult ->

            when (saveResult) {
                is Either.Data -> assertNotNull(saveResult.data)
                is Either.Error -> throw AssertionFailedError("RoomImageSource couldn't save the image")
            }

            // Checks the source has saved the image
            source.fetchImage(saveResult.data.url).observeOnce { result ->

                // Checks the result is not an error
                assertFalse(result is Either.Error)

                when (result) {
                    // Checks the result data is not null. The image must have been saved at this point
                    is Either.Data -> assertNotNull(result.data)

                    // If the result is an error, we throw an AssertionError since we don't expect errors here
                    is Either.Error -> throw AssertionError("testInsertAndFileCreation(): Insert failed")
                }

                assertNotNull(
                    MediaStoreUtil.getImageFromDisk(
                        ApplicationProvider.getApplicationContext(),
                        image
                    )
                )
            }
        }
    }

    private fun <T> LiveData<T>.observeOnce(onChangeHandler: (T) -> Unit) {
        val observer = OneTimeObserver(handler = onChangeHandler)
        observe(observer, observer)
    }
}
