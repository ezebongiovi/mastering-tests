package com.edipasquale.tdd_room.ui.page

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.edipasquale.tdd_room.R
import com.edipasquale.tdd_room.util.actions.HolderChildAction
import com.edipasquale.tdd_room.view.adapter.holder.ImageViewHolder

class LibraryPage {

    fun moveToDownloadPage(): DownloadPage {
        onView(withId(R.id.viewPager)).perform(ViewActions.swipeLeft())

        return DownloadPage()
    }

    fun deleteImage(): LibraryPage {
        onView(withId(R.id.recyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ImageViewHolder>(
                0,
                HolderChildAction.clickChildViewWithId(R.id.actionDelete)
            )
        )

        return this
    }
}