package com.edipasquale.tdd_room.model

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.edipasquale.tdd_room.R
import com.edipasquale.tdd_room.dto.Image
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ImageModelTest {

    @Test
    fun testImageNotFoundError() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val model = ImageModel(error = ERROR_NOT_FOUND)

        assertEquals(model.getErrorMessage(context), context.getString(R.string.error_not_found))
    }

    @Test
    fun testImageDownloadError() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val model = ImageModel(error = ERROR_DOWNLOADING_IMAGE)

        assertEquals(
            model.getErrorMessage(context),
            context.getString(R.string.error_image_download)
        )
    }

    @Test
    fun testImageStorageError() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val model = ImageModel(error = ERROR_INTERNAL_STORAGE)

        assertEquals(
            model.getErrorMessage(context),
            context.getString(R.string.error_image_internal_storage)
        )
    }

    @Test
    fun testIfSuccessfulOrNot() {
        val errorModel = ImageModel(error = ERROR_NOT_FOUND)
        val successModel = ImageModel(Image("some url"))

        assertTrue(successModel.isSuccess())
        assertFalse(errorModel.isSuccess())
    }
}