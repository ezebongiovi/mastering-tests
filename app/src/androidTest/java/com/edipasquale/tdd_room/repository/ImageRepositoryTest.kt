package com.edipasquale.tdd_room.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.edipasquale.tdd_room.database.ImageDatabase
import com.edipasquale.tdd_room.dto.Image
import com.edipasquale.tdd_room.observer.OneTimeObserver
import com.edipasquale.tdd_room.source.impl.LocalImageSource
import com.edipasquale.tdd_room.source.impl.NetworkImageSource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

@RunWith(AndroidJUnit4::class)
class ImageRepositoryTest {

    @get:Rule
    val rule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ImageDatabase
    private lateinit var repository: ImageRepository
    private lateinit var localSource: LocalImageSource
    private lateinit var networkSource: NetworkImageSource

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        database = Room.inMemoryDatabaseBuilder<ImageDatabase>(
            ApplicationProvider.getApplicationContext(),
            ImageDatabase::class.java
        ).build()

        localSource = spy(
            LocalImageSource(
                database.getImageDao(),
                ApplicationProvider.getApplicationContext()
            )
        )
        networkSource = spy(NetworkImageSource())

        repository = ImageRepository(localSource, networkSource)
    }

    @Test
    fun testDownloadFromNetwork() {
        repository.getImage("network").observeOnce {
            verify(networkSource, times(1)).fetchImage("network")
        }
    }

    @Test
    fun testDownloadFromLocal() {
        val image = Image("local")

        // Save the image on local source. It means this image is already cached
        localSource.saveImage(image)

        repository.getImage(image.url).observeOnce {

            // It should never call to the network source since the image is cached.
            verify(networkSource, never()).fetchImage(image.url)
        }
    }

    private fun <T> LiveData<T>.observeOnce(onChangeHandler: (T) -> Unit) {
        val observer = OneTimeObserver(handler = onChangeHandler)
        observe(observer, observer)
    }
}