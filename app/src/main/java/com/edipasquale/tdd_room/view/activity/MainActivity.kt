package com.edipasquale.tdd_room.view.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewAnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import com.edipasquale.tdd_room.R
import com.edipasquale.tdd_room.view.adapter.MainPageAdapter
import com.edipasquale.tdd_room.view.fragment.DownloadFragment
import com.edipasquale.tdd_room.view.fragment.IdlingResource
import com.edipasquale.tdd_room.view.fragment.MyLibraryFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var mPageAdapter: MainPageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        mPageAdapter = MainPageAdapter(getPages(), supportFragmentManager)

        viewPager.adapter = mPageAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main, menu)

        val menuItem = menu?.findItem(R.id.action_search)
        val searchView = (menuItem!!.actionView as SearchView)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                mPageAdapter.onSearchSubmit(viewPager.currentItem, query!!)

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (viewPager.currentItem == 1) {
                    mPageAdapter.onSearchSubmit(viewPager.currentItem, newText!!)
                    return true
                }

                return false
            }
        })

        menuItem.setOnActionExpandListener(
            object : MenuItem.OnActionExpandListener {
                override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {

                    // Set search hint
                    if (viewPager.currentItem == 0)
                        searchView.queryHint = getString(R.string.hint_search_url)
                    else
                        searchView.queryHint = getString(R.string.hint_search_library)


                    appBar.setExpanded(false)

                    window.statusBarColor =
                        ContextCompat.getColor(this@MainActivity, R.color.quantum_grey_600)

                    toolbar.setBackgroundColor(
                        ContextCompat.getColor(
                            this@MainActivity,
                            android.R.color.white
                        )
                    )

                    val animator = ViewAnimationUtils.createCircularReveal(
                        toolbar,
                        toolbar.width, toolbar.height / 2, 0f, toolbar.width.toFloat()
                    )

                    animator.duration = 250
                    animator.start()

                    return true
                }

                override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                    appBar.setExpanded(true)

                    val animator = ViewAnimationUtils.createCircularReveal(
                        toolbar,
                        toolbar.width, toolbar.height / 2, toolbar.width.toFloat(), 0f
                    )

                    animator.duration = 250
                    animator.doOnEnd {

                        window.statusBarColor = ContextCompat.getColor(
                            this@MainActivity,
                            R.color.colorPrimaryDark
                        )

                        toolbar.setBackgroundColor(
                            ContextCompat.getColor(
                                this@MainActivity,
                                R.color.colorPrimary
                            )
                        )
                    }

                    animator.start()

                    return true
                }
            })

        return true
    }

    // Returns whether any of the fragments is idling or not
    fun isIdling(): Boolean {
        for (index in 0 until mPageAdapter.count) {
            val element = mPageAdapter.getItem(index)

            if (element is IdlingResource && !element.isIdling())
                return false
        }

        return true
    }

    // Observes changes on idling sources value
    fun observeIdling(observer: Observer) {
        for (index in 0 until mPageAdapter.count) {
            val element = mPageAdapter.getItem(index)

            if (element is IdlingResource)
                element.observeIdling(observer)
        }
    }

    private fun getPages(): List<MainPageAdapter.Page> = listOf(
        MainPageAdapter.Page(getString(R.string.tab_download), DownloadFragment()),
        MainPageAdapter.Page(getString(R.string.tab_my_images), MyLibraryFragment())
    )
}

