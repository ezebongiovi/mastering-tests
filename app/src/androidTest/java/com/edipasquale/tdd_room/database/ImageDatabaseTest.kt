package com.edipasquale.tdd_room.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.edipasquale.tdd_room.dto.Image
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ImageDatabaseTest {
    private lateinit var database: ImageDatabase
    private lateinit var imageDao: ImageDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder<ImageDatabase>(
            ApplicationProvider.getApplicationContext<Context>(),
            ImageDatabase::class.java
        ).build()

        imageDao = database.getImageDao()
    }

    @Test
    fun testInsert() {
        val image = Image("insert")

        assertNull(database.getImageDao().getImage(image.url))

        database.getImageDao().insertImage(image)

        assertEquals(database.getImageDao().getImage("insert"), image)
    }

    @Test
    fun testDelete() {
        val image = Image("delete")

        image.id = database.getImageDao().insertImage(image)

        assertNotNull(database.getImageDao().getImage(image.url))

        database.getImageDao().deleteImage(image)

        assertNull(database.getImageDao().getImage(image.url))
    }
}
