package com.qtonz.photos.galleryjson.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.qtonz.photos.gallery.fragment.ImageFolderFragment
import com.qtonz.photos.gallery.fragment.RecentImagesFragment
import com.qtonz.photos.galleryjson.fragment.ImageFolderJsonFragment
import com.qtonz.photos.galleryjson.fragment.RecentImagesJsonFragment

class FolderViewPagerJsonAdapter(
    activity: FragmentActivity,
    private val folderList: List<Pair<String, String>>
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return folderList.size
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            RecentImagesJsonFragment.newInstance()
        } else {
            val folderPath = folderList[position].second
            ImageFolderJsonFragment.newInstance(folderPath)
        }
    }
}



