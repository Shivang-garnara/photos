package com.qtonz.photos.gallery.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.qtonz.photos.gallery.activity.ImageDetailActivity
import com.qtonz.photos.gallery.adapter.ImageAdapter
import com.qtonz.photos.gallery.database.RecentlyOpenedImageDatabase
import com.qtonz.photos.databinding.FragmentImageFolderBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecentImagesFragment : Fragment() {
    private lateinit var binding: FragmentImageFolderBinding
    private lateinit var database: RecentlyOpenedImageDatabase
    private lateinit var imageAdapter: ImageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentImageFolderBinding.inflate(inflater, container, false)
        database = RecentlyOpenedImageDatabase.getDatabase(requireContext())
        binding.rvImage.layoutManager = GridLayoutManager(requireContext(), 3)

        imageAdapter = ImageAdapter { imagePath ,position->
            val imageList = imageAdapter.currentList
            val intent = Intent(requireContext(), ImageDetailActivity::class.java).apply {
                putStringArrayListExtra("IMAGE_PATH_LIST", ArrayList(imageList))
                putExtra("IMAGE_POSITION", position)
            }
            startActivity(intent)
        }
        binding.rvImage.adapter = imageAdapter
        observeRecentImages()
        return binding.root
    }

    private fun observeRecentImages() {
        database.recentlyOpenedImageDao().getAllRecentlyOpenedImages().observe(viewLifecycleOwner, Observer { images ->
            imageAdapter.submitList(images.map { it.imagePath })
        })
    }

    companion object {
        fun newInstance(): RecentImagesFragment {
            return RecentImagesFragment()
        }
    }
}
