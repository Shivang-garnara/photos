package com.qtonz.photos.gallery

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.qtonz.photos.R
import com.qtonz.photos.gallery.adapter.FolderViewPagerAdapter
import com.qtonz.photos.databinding.ActivityDeviceImageBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainGalleryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDeviceImageBinding
    private lateinit var folderPagerAdapter: FolderViewPagerAdapter
    private val REQUEST_CODE_READ_EXTERNAL_STORAGE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDeviceImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (hasReadStoragePermission()) {
            setupViewPager()
        } else {
            requestReadStoragePermission()
        }
    }

    private fun requestReadStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(
                arrayOf(Manifest.permission.READ_MEDIA_IMAGES), REQUEST_CODE_READ_EXTERNAL_STORAGE
            )
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_CODE_READ_EXTERNAL_STORAGE
            )
        }
    }

    private fun hasReadStoragePermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED
        } else {
            checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun setupViewPager() {
        CoroutineScope(Dispatchers.IO).launch {
            val folderList = getImageFolders()
            val recentFolder = Pair("Recent", getRecentImagesFolderPath())
            val updatedFolderList = mutableListOf(recentFolder) + folderList
            withContext(Dispatchers.Main) {
                folderPagerAdapter =
                    FolderViewPagerAdapter(this@MainGalleryActivity, updatedFolderList)
                binding.vpMain.adapter = folderPagerAdapter

                TabLayoutMediator(binding.tabLayout, binding.vpMain) { tab, position ->
                    tab.text = updatedFolderList[position].first
                }.attach()
            }
        }
    }

    private fun getImageFolders(): List<Pair<String, String>> {
        val folderList = mutableListOf<Pair<String, String>>()
        val projection = arrayOf(
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.DATA
        )

        val cursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, null
        )

        cursor?.use {
            val columnIndexFolderName =
                it.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
            val columnIndexData = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)

            val folderPaths = mutableSetOf<String>()

            while (it.moveToNext()) {
                val folderName = it.getString(columnIndexFolderName)
                val folderPath = it.getString(columnIndexData).substringBeforeLast("/")

                if (!folderPaths.contains(folderPath)) {
                    folderPaths.add(folderPath)
                    folderList.add(Pair(folderName, folderPath))
                }
            }
        }
        return folderList
    }

        override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_READ_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setupViewPager()
                } else {
                    Toast.makeText(
                        this, getString(R.string.app_name), Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
    private fun getRecentImagesFolderPath(): String {
        return "RecentImagesPath"
    }
}