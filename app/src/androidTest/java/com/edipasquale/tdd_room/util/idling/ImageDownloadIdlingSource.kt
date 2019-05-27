package com.edipasquale.tdd_room.util.idling

import androidx.test.espresso.IdlingResource
import androidx.test.rule.ActivityTestRule
import com.edipasquale.tdd_room.view.activity.MainActivity
import java.util.*

/**
 * Idling for image downloader. Maybe not the best solution, but works for now.
 *
 * TODO: I recommend to evaluate the usage of some other download method. Like Glide, Picasso or Retrofit and their own idling resources.
 * TODO: The main focus on this project is on TDD
 */
class ImageDownloadIdlingSource(private val activityTestRule: ActivityTestRule<MainActivity>) :
    IdlingResource {
    private var mCallback: IdlingResource.ResourceCallback? = null

    private val observer = Observer { o, arg ->
        if (mCallback != null && arg as Boolean)
            mCallback!!.onTransitionToIdle()
    }

    init {
        activityTestRule.activity.observeIdling(observer)
    }

    override fun getName(): String = "ImageFetcher"

    override fun isIdleNow(): Boolean = activityTestRule.activity.isIdling()

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        mCallback = callback
    }
}