package com.qtonz.photos

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.qtonz.photos.databinding.ActivityMainAppBinding
import com.qtonz.photos.gallery.MainGalleryActivity
import com.qtonz.photos.galleryjson.MainGalleryJsonActivity
import com.qtonz.photos.viewpager.MainViewPagerActivity

class MainAppActivity : AppCompatActivity() {
    private val binding:ActivityMainAppBinding by lazy {
        ActivityMainAppBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        addListener()
    }

    private fun addListener() {
        binding.btnGallery.setOnClickListener{
            startActivity(Intent(this@MainAppActivity, MainGalleryActivity::class.java))
        }
        binding.btnViewPager.setOnClickListener{
            startActivity(Intent(this@MainAppActivity, MainViewPagerActivity::class.java))
        }
        binding.btnGalleryJson.setOnClickListener{
            startActivity(Intent(this@MainAppActivity,MainGalleryJsonActivity::class.java))
        }
    }
}