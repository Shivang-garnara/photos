package com.qtonz.photos.galleryjson.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.qtonz.photos.databinding.ActivityImageDetailBinding
import com.qtonz.photos.galleryjson.adapter.DetailsViewPagerJsonAdapter
import com.qtonz.photos.galleryjson.utils.RecentImagesJsonHelper

//class ImageDetailJsonActivity : AppCompatActivity() {
//    private val binding: ActivityImageDetailBinding by lazy {
//        ActivityImageDetailBinding.inflate(layoutInflater)
//    }
//    private lateinit var viewPager: ViewPager2
//    private lateinit var imagePaths: List<String>
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(binding.root)
//
//        imagePaths = intent.getStringArrayListExtra("IMAGE_PATH_LIST") ?: emptyList()
//        val initialPosition = intent.getIntExtra("IMAGE_POSITION", 0)
//
//        if (imagePaths.isNotEmpty()) {
//            viewPager = binding.viewPager
//            viewPager.adapter = DetailsViewPagerJsonAdapter(imagePaths) { imagePath ->
//
//            }
//            viewPager.setCurrentItem(initialPosition, false)
//        }
//    }
//}

class ImageDetailJsonActivity : AppCompatActivity() {
    private val binding: ActivityImageDetailBinding by lazy {
        ActivityImageDetailBinding.inflate(layoutInflater)
    }
    private lateinit var viewPager: ViewPager2
    private lateinit var imagePaths: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        imagePaths = intent.getStringArrayListExtra("IMAGE_PATH_LIST") ?: emptyList()
        val initialPosition = intent.getIntExtra("IMAGE_POSITION", 0)

        if (imagePaths.isNotEmpty()) {
            viewPager = binding.viewPager
            viewPager.adapter = DetailsViewPagerJsonAdapter(imagePaths) { imagePath ->
                // Save each opened image to recent
                RecentImagesJsonHelper.saveRecentImage(this, imagePath)
            }
            viewPager.setCurrentItem(initialPosition, false)
        }
    }
}
