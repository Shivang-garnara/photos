package com.qtonz.photos.gallery.activity

//import android.os.Bundle
//import android.util.Log
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.lifecycleScope
//import com.bumptech.glide.Glide
//import com.qtonz.photos.R
//import com.qtonz.photos.database.RecentlyOpenedImage
//import com.qtonz.photos.database.RecentlyOpenedImageDatabase
//import com.qtonz.photos.databinding.ActivityImageDetailBinding
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch

//class ImageDetailActivity : AppCompatActivity() {
//    private val binding: ActivityImageDetailBinding by lazy {
//        ActivityImageDetailBinding.inflate(layoutInflater)
//    }
//
//    private lateinit var database: RecentlyOpenedImageDatabase
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(binding.root)
//        database = RecentlyOpenedImageDatabase.getDatabase(this)
//        val imagePath = intent.getStringExtra("IMAGE_PATH")
//        if (imagePath != null) {
//            Glide.with(this)
//                .load(imagePath)
//                .into(binding.imgImage)
//            binding.tvUri.text = imagePath
//            saveImageToDatabase(imagePath)
//        } else {
//            binding.tvUri.text = getString(R.string.no_image_path_available)
//        }
//    }
//
//    private fun saveImageToDatabase(imagePath: String) {
//        lifecycleScope.launch(Dispatchers.IO) {
//            val image = RecentlyOpenedImage(imagePath = imagePath)
//            database.recentlyOpenedImageDao().insertImage(image)
//        }
//    }
//}


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.qtonz.photos.gallery.adapter.DetailsViewPagerAdapter
import com.qtonz.photos.gallery.database.RecentlyOpenedImage
import com.qtonz.photos.gallery.database.RecentlyOpenedImageDatabase
import com.qtonz.photos.databinding.ActivityImageDetailBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ImageDetailActivity : AppCompatActivity() {
    private val binding: ActivityImageDetailBinding by lazy {
        ActivityImageDetailBinding.inflate(layoutInflater)
    }
    private lateinit var database: RecentlyOpenedImageDatabase
    private lateinit var viewPager: ViewPager2
    private lateinit var imagePaths: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        database = RecentlyOpenedImageDatabase.getDatabase(this)

        imagePaths = intent.getStringArrayListExtra("IMAGE_PATH_LIST") ?: emptyList()
        val initialPosition = intent.getIntExtra("IMAGE_POSITION", 0)

        if (imagePaths.isNotEmpty()) {
            viewPager = binding.viewPager
            viewPager.adapter = DetailsViewPagerAdapter(imagePaths) { imagePath ->
                saveImageToDatabaseIfNotExists(imagePath)
            }
            viewPager.setCurrentItem(initialPosition, false)
        }
    }
    private fun saveImageToDatabaseIfNotExists(imagePath: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val existingImage = database.recentlyOpenedImageDao().getImageByPath(imagePath)
            if (existingImage == null) {
                database.recentlyOpenedImageDao().insertImage(
                    RecentlyOpenedImage(imagePath = imagePath)
                )
            }
        }
    }
}
