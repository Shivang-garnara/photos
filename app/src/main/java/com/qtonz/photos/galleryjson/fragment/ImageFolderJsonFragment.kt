package com.qtonz.photos.galleryjson.fragment

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
import com.qtonz.photos.galleryjson.activity.ImageDetailJsonActivity
import com.qtonz.photos.galleryjson.adapter.ImageJsonAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ImageFolderJsonFragment : Fragment() {
    private lateinit var adapter: ImageJsonAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_image_folder, container, false)
        recyclerView = view.findViewById(R.id.rvImage)
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        adapter = ImageJsonAdapter { imagePath, position ->
            val imageList = adapter.currentList
            val intent = Intent(requireContext(), ImageDetailJsonActivity::class.java).apply {
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
                    adapter.submitList(newImageList)
                }
            }
        }
    }

    companion object {
        private const val ARG_FOLDER_PATH = "folder_path"

        fun newInstance(folderPath: String): ImageFolderJsonFragment {
            val fragment = ImageFolderJsonFragment()
            val args = Bundle()
            args.putString(ARG_FOLDER_PATH, folderPath)
            fragment.arguments = args
            return fragment
        }
    }
}
