    package com.edipasquale.tdd_room.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MainPageAdapter(private val data: List<Page>, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    interface SearchListener {
        fun onSearch(query: String)
    }

    override fun getItem(position: Int) = data[position].fragment

    override fun getCount(): Int = data.size

    override fun getPageTitle(position: Int): CharSequence? = data[position].title

    fun onSearchSubmit(position: Int, query: String) {
        (data[position].fragment as SearchListener).onSearch(query)
    }

    data class Page(val title: String, val fragment: Fragment)
}