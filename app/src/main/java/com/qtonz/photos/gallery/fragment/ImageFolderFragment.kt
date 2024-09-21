//package com.qtonz.photos.gallery.fragment
//
//import android.content.Intent
//import android.os.Bundle
//import android.provider.MediaStore
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.GridLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.qtonz.photos.R
//import com.qtonz.photos.gallery.activity.ImageDetailActivity
//import com.qtonz.photos.gallery.adapter.ImageAdapter
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//
//class ImageFolderFragment : Fragment() {
//    //    private val REQUEST_CODE_READ_EXTERNAL_STORAGE = 100
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
//        adapter =  ImageAdapter { imagePath, position ->
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


//    private fun checkPermissions(folderPath: String) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            if (requireContext().checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
//                requestPermissions(
//                    arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
//                    REQUEST_CODE_READ_EXTERNAL_STORAGE
//                )
//            } else {
//                loadImagesFromFolder(folderPath)
//            }
//        } else {
//            if (requireContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                requestPermissions(
//                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
//                    REQUEST_CODE_READ_EXTERNAL_STORAGE
//                )
//            } else {
//                loadImagesFromFolder(folderPath)
//            }
//        }
//    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == REQUEST_CODE_READ_EXTERNAL_STORAGE) {
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                val folderPath = arguments?.getString(ARG_FOLDER_PATH)
//                folderPath?.let {
//                    loadImagesFromFolder(it)
//                }
//            } else {
//                Toast.makeText(context, "Permission required to access images", Toast.LENGTH_SHORT)
//                    .show()
//            }
//        }
//    }







package com.qtonz.photos.gallery.fragment

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qtonz.photos.R
import com.qtonz.photos.gallery.activity.ImageDetailActivity
import com.qtonz.photos.gallery.adapter.ImageAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ImageFolderFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ImageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_image_folder, container, false)
        recyclerView = view.findViewById(R.id.rvImage)
        recyclerView.layoutManager = GridLayoutManager(context, 3)

        // Initialize the adapter with an empty list
        adapter = ImageAdapter(mutableListOf()) { imagePath, position ->
            val imageList = adapter.getImageList()  // Get the image list directly from the adapter
            val intent = Intent(requireContext(), ImageDetailActivity::class.java).apply {
                putStringArrayListExtra("IMAGE_PATH_LIST", ArrayList(imageList))
                putExtra("IMAGE_POSITION", position)
            }
            startActivity(intent)
        }

        recyclerView.adapter = adapter

        val folderPath = arguments?.getString(ARG_FOLDER_PATH)
        folderPath?.let {
            loadImagesFromFolder(it)
        }
        return view
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
                    // Update the adapter's image list
                    adapter.updateImages(newImageList)
                }
            }
        }
    }

    companion object {
        private const val ARG_FOLDER_PATH = "folder_path"

        fun newInstance(folderPath: String): ImageFolderFragment {
            val fragment = ImageFolderFragment()
            val args = Bundle()
            args.putString(ARG_FOLDER_PATH, folderPath)
            fragment.arguments = args
            return fragment
        }
    }
}
