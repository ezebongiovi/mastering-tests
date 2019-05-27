package com.edipasquale.tdd_room.ui.page

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.edipasquale.tdd_room.R

class DownloadPage {

    fun openSearch(): DownloadPage {
        onView(withId(R.id.action_search)).perform(ViewActions.click())

        return this
    }

    fun pressBack(): DownloadPage {
        Espresso.pressBack()

        return this
    }

    fun setSearchQuery(query: String): DownloadPage {
        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(
            ViewActions.replaceText(
                query
            )
        )

        return this
    }

    fun executeSearch(): DownloadPage {
        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(ViewActions.pressImeActionButton())

        return this
    }

    fun moveToLibraryPage(): LibraryPage {
        onView(withId(R.id.viewPager)).perform(ViewActions.swipeLeft())

        return LibraryPage()
    }
}