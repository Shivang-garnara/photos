package com.qtonz.photos.gallery.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.qtonz.photos.gallery.fragment.ImageFolderFragment
import com.qtonz.photos.gallery.fragment.RecentImagesFragment

class FolderViewPagerAdapter(
    activity: FragmentActivity,
    private val folderList: List<Pair<String, String>>
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return folderList.size
    }

//    override fun createFragment(position: Int): Fragment {
//        val folderPath = folderList[position].second
//        return ImageFolderFragment.newInstance(folderPath)
//    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            RecentImagesFragment.newInstance()
        } else {
            val folderPath = folderList[position].second
            ImageFolderFragment.newInstance(folderPath)
        }
//        val folderPath = folderList[position].second
//        return if(position == 0){
//            return com.qtonz.photos.test.ImageFolderFragment.newInstance(folderPath,0)
//        }else{
//
//            return com.qtonz.photos.test.ImageFolderFragment.newInstance(folderPath,position)
//        }
    }
}



