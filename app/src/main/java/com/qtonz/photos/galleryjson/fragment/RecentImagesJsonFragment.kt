//package com.qtonz.photos.galleryjson.fragment
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.GridLayoutManager
//import com.qtonz.photos.databinding.FragmentImageFolderBinding
//import com.qtonz.photos.galleryjson.adapter.ImageJsonAdapter
//import com.qtonz.photos.galleryjson.utils.RecentImagesJsonHelper
//
//class RecentImagesJsonFragment : Fragment() {
//    private lateinit var binding: FragmentImageFolderBinding
//    private lateinit var adapter: ImageJsonAdapter
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentImageFolderBinding.inflate(inflater, container, false)
//        adapter = ImageJsonAdapter { imagePath, position ->
//            // Handle item click here if needed
//        }
//
//        binding.rvImage.layoutManager = GridLayoutManager(context, 3)
//        binding.rvImage.adapter = adapter
//
//        // Load recent images
//        val recentImages = RecentImagesJsonHelper.getRecentImages(requireContext())
//        adapter.submitList(recentImages)
//
//        return binding.root
//    }
//
//    companion object {
//        fun newInstance(): RecentImagesJsonFragment {
//            return RecentImagesJsonFragment()
//        }
//    }
//}



package com.qtonz.photos.galleryjson.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.qtonz.photos.databinding.FragmentImageFolderBinding
import com.qtonz.photos.galleryjson.adapter.ImageJsonAdapter
import com.qtonz.photos.galleryjson.utils.RecentImagesJsonHelper

class RecentImagesJsonFragment : Fragment() {
    private lateinit var binding: FragmentImageFolderBinding
    private lateinit var adapter: ImageJsonAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentImageFolderBinding.inflate(inflater, container, false)

        adapter = ImageJsonAdapter(mutableListOf()) { imagePath, position ->
        }

        binding.rvImage.layoutManager = GridLayoutManager(context, 3)
        binding.rvImage.adapter = adapter

        val recentImages = RecentImagesJsonHelper.getRecentImages(requireContext())

        adapter.updateImageList(recentImages)

        return binding.root
    }

    companion object {
        fun newInstance(): RecentImagesJsonFragment {
            return RecentImagesJsonFragment()
        }
    }
}
