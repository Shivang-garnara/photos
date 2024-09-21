package com.qtonz.photos.gallery.test

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qtonz.photos.R
import com.qtonz.photos.gallery.activity.ImageDetailActivity
import com.qtonz.photos.gallery.adapter.ImageAdapter
import com.qtonz.photos.gallery.database.RecentlyOpenedImageDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//class ImageFolderFragment : Fragment() {
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var adapter: ImageAdapter
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_image_folder, container, false)
//        recyclerView = view.findViewById(R.id.rvImage)
//        recyclerView.layoutManager = GridLayoutManager(context, 3)
//        adapter = ImageAdapter { imagePath, position ->
//            val imageList = adapter.currentList
//            val intent = Intent(requireContext(), ImageDetailActivity::class.java).apply {
//                putStringArrayListExtra("IMAGE_PATH_LIST", ArrayList(imageList))
//                putExtra("IMAGE_POSITION", position)
//            }
//            startActivity(intent)
//        }
//        recyclerView.adapter = adapter
//
//        val folderPath = arguments?.getString(ARG_FOLDER_PATH)
//        folderPath?.let {
////            checkPermissions(it)
//            loadImagesFromFolder(it)
//        }
//        return view
//    }
//
//    private fun loadImagesFromFolder(folderPath: String) {
//        CoroutineScope(Dispatchers.IO).launch {
//            val projection = arrayOf(
//                MediaStore.Images.Media._ID,
//                MediaStore.Images.Media.DATA
//            )
//
//            val selection = "${MediaStore.Images.Media.DATA} LIKE ?"
//            val selectionArgs = arrayOf("$folderPath/%")
//
//            val cursor = requireContext().contentResolver.query(
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                projection,
//                selection,
//                selectionArgs,
//                "${MediaStore.Images.Media.DATE_ADDED} DESC"
//            )
//
//            cursor?.use {
//                val columnIndexData = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
//
//                val newImageList = mutableListOf<String>()
//                while (it.moveToNext()) {
//                    val imagePath = it.getString(columnIndexData)
//                    newImageList.add(imagePath)
//                }
//
//                withContext(Dispatchers.Main) {
//                    adapter.submitList(newImageList)
//                }
//            }
//        }
//    }
//
//    companion object {
//        private const val ARG_FOLDER_PATH = "folder_path"
//
//        fun newInstance(folderPath: String): ImageFolderFragment {
//            val fragment = ImageFolderFragment()
//            val args = Bundle()
//            args.putString(ARG_FOLDER_PATH, folderPath)
//            fragment.arguments = args
//            return fragment
//        }
//    }
//}


class ImageFolderFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ImageAdapter
    private var folderPath: String? = null
    private var tabPosition: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_image_folder, container, false)
        recyclerView = view.findViewById(R.id.rvImage)
        recyclerView.layoutManager = GridLayoutManager(context, 3)

        folderPath = arguments?.getString(ARG_FOLDER_PATH)
        tabPosition = arguments?.getInt(ARG_TAB_POSITION, -1) ?: -1

        adapter = ImageAdapter { imagePath, position ->
            val imageList = adapter.currentList
            val intent = Intent(requireContext(), ImageDetailActivity::class.java).apply {
                putStringArrayListExtra("IMAGE_PATH_LIST", ArrayList(imageList))
                putExtra("IMAGE_POSITION", position)
            }
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        if (tabPosition == 0) {
            loadRecentImages()
        } else {
            folderPath?.let { loadImagesFromFolder(it) }
        }

        return view
    }

    private fun loadRecentImages() {
        val database = RecentlyOpenedImageDatabase.getDatabase(requireContext())
        database.recentlyOpenedImageDao().getAllRecentlyOpenedImages().observe(viewLifecycleOwner, Observer { images ->
            adapter.submitList(images.map { it.imagePath })
        })
    }

    private fun loadImagesFromFolder(folderPath: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val projection = arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA
            )

            val selection = "${MediaStore.Images.Media.DATA} LIKE ?"
            val selectionArgs = arrayOf("$folderPath/%")

            val cursor = requireContext().contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                "${MediaStore.Images.Media.DATE_ADDED} DESC"
            )

            cursor?.use {
                val columnIndexData = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)

                val newImageList = mutableListOf<String>()
                while (it.moveToNext()) {
                    val imagePath = it.getString(columnIndexData)
                    newImageList.add(imagePath)
                }

                withContext(Dispatchers.Main) {
                    adapter.submitList(newImageList)
                }
            }
        }
    }

    companion object {
        private const val ARG_FOLDER_PATH = "folder_path"
        private const val ARG_TAB_POSITION = "tab_position"
        fun newInstance(folderPath: String, tabPosition: Int): ImageFolderFragment {
            val fragment = ImageFolderFragment()
            val args = Bundle()
            args.putString(ARG_FOLDER_PATH, folderPath)
            args.putInt(ARG_TAB_POSITION, tabPosition)
            fragment.arguments = args
            return fragment
        }
    }
}
