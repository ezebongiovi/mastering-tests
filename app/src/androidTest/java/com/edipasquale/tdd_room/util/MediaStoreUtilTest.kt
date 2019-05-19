package com.edipasquale.tdd_room.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.edipasquale.tdd_room.dto.Image
import org.junit.Assert
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MediaStoreUtilTest {

    private lateinit var context: Context
    private val image = Image("testing").apply {
        picture = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
    }

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun testFileCreation() {
        Assert.assertNull(MediaStoreUtil.getImageFromDisk(context, image))

        MediaStoreUtil.saveImageOnDisk(context, image)

        Assert.assertNotNull(MediaStoreUtil.getImageFromDisk(context, image))
    }

    @Test
    fun testDeletion() {
        MediaStoreUtil.saveImageOnDisk(context, image)

        Assert.assertNotNull(MediaStoreUtil.getImageFromDisk(context, image))

        MediaStoreUtil.deleteImageFromDisk(context, image)

        Assert.assertNull(MediaStoreUtil.getImageFromDisk(context, image))
    }

    @Test
    fun testImageUri() {
        assertTrue(MediaStoreUtil.saveImageOnDisk(context, image))

        val imageUri = MediaStoreUtil.getImageFile(context, image)

        assertTrue(
            MediaStoreUtil.getImageFromDisk(context, image)
            !!.sameAs(BitmapFactory.decodeFile(imageUri.absolutePath))
        )
    }
}