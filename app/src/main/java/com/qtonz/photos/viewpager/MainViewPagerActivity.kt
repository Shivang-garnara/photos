package com.qtonz.photos.viewpager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.qtonz.photos.databinding.ActivityMainViewPagerBinding
import com.qtonz.photos.viewpager.adapter.ViewPagerAdapter

class MainViewPagerActivity : AppCompatActivity() {
    private val tabTitle = arrayOf("simple", "nested", "grid","Staggered")
    private val binding by lazy {
        ActivityMainViewPagerBinding.inflate(layoutInflater)
    }
    private lateinit var viewpageradapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupViewPager()
    }

    private fun setupViewPager() {
        viewpageradapter = ViewPagerAdapter(this, tabTitle)
        binding.vpMain.adapter = viewpageradapter

        TabLayoutMediator(binding.tabLayout, binding.vpMain) { tab, position ->
            tab.text = viewpageradapter.getTabTitle(position)
        }.attach()
    }
}