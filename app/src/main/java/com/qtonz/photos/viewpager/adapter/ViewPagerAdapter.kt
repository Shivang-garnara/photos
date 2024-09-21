package com.qtonz.photos.viewpager.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.qtonz.photos.viewpager.fragment.ViewPagerFragment

class ViewPagerAdapter(activity: FragmentActivity, private val tabTitles: Array<String>) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = tabTitles.size

    override fun createFragment(position: Int): Fragment = ViewPagerFragment.newInstance(position)

    fun getTabTitle(position: Int): String = tabTitles[position]
}