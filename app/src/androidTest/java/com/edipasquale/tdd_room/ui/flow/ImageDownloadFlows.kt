package com.edipasquale.tdd_room.ui.flow

import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.edipasquale.tdd_room.rule.DisableAnimationsRule
import com.edipasquale.tdd_room.ui.page.DownloadPage
import com.edipasquale.tdd_room.util.idling.ImageDownloadIdlingSource
import com.edipasquale.tdd_room.view.activity.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ImageDownloadFlows {

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @get:Rule
    val animationsRule = DisableAnimationsRule()

    private lateinit var mIdlingResource: ImageDownloadIdlingSource

    @Before
    fun setup() {
        mIdlingResource =
            ImageDownloadIdlingSource(
                activityRule
            )
        IdlingRegistry.getInstance().register(mIdlingResource)
    }

    @After
    fun shutDown() {
        IdlingRegistry.getInstance().unregister(mIdlingResource)
    }

    @Test
    fun testDeletionFlow() {
        DownloadPage()
            .openSearch()
            .setSearchQuery("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/emma-stone-attends-the-louis-vuitton-cruise-2020-fashion-news-photo-1573038714.jpg")
            .executeSearch()
            .pressBack() // Closes keyboard
            .pressBack() // Closes search view
            .moveToLibraryPage()
            .deleteImage()
    }
}