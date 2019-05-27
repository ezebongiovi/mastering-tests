package com.edipasquale.tdd_room.ui.case

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.edipasquale.tdd_room.ui.page.DownloadPage
import com.edipasquale.tdd_room.view.activity.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DownloadImageCase {

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testDownloadImage() {
        DownloadPage()
            .openSearch()
            .setSearchQuery("https://cdn.tn.com.ar/sites/default/files/styles/1366x765/public/2018/11/06/emmastone-tapa.jpg")
            .executeSearch()
            .moveToLibraryPage()
    }
}