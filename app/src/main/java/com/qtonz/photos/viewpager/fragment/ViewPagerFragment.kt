package com.qtonz.photos.viewpager.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.qtonz.photos.databinding.FragmentImageFolderBinding
import com.qtonz.photos.viewpager.adapter.GridViewPagerAdapter
import com.qtonz.photos.viewpager.adapter.NestedViewPagerAdapter
import com.qtonz.photos.viewpager.adapter.SimpleViewPagerAdapter
import com.qtonz.photos.viewpager.adapter.StaggeredViewPagerAdapter
import com.qtonz.photos.viewpager.data.CourseRVModal
import com.qtonz.photos.viewpager.data.Items
import com.qtonz.photos.viewpager.data.itemsPractice


class ViewPagerFragment : Fragment() {
    private lateinit var binding: FragmentImageFolderBinding
    private val data = ArrayList<itemsPractice>()
    private lateinit var courseList: ArrayList<CourseRVModal>
    private lateinit var images: ArrayList<Int>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentImageFolderBinding.inflate(inflater, container, false)
        Log.e("arguments" ,"$arguments")
        val tabPosition = arguments?.getInt(ARG_TAB_POSITION) ?: 0
        when (tabPosition) {
            0 -> {
                val adapter = SimpleViewPagerAdapter(data)
                binding.rvImage.layoutManager = LinearLayoutManager(requireContext())
                data.add(
                    itemsPractice(
                        com.qtonz.photos.R.drawable.ic_launcher_background,
                        "vatar",
                        "9",
                        "hindi",
                        "3h 15m 36s"
                    )
                )
                data.add(
                    itemsPractice(
                        com.qtonz.photos.R.drawable.ic_launcher_background,
                        "vatar",
                        "9",
                        "hindi",
                        "3h 15m 36s"
                    )
                )
                data.add(
                    itemsPractice(
                        com.qtonz.photos.R.drawable.ic_launcher_background,
                        "vatar",
                        "9",
                        "hindi",
                        "3h 15m 36s"
                    )
                )
                data.add(
                    itemsPractice(
                        com.qtonz.photos.R.drawable.ic_launcher_background,
                        "vatar",
                        "9",
                        "hindi",
                        "3h 15m 36s"
                    )
                )
                data.add(
                    itemsPractice(
                        com.qtonz.photos.R.drawable.ic_launcher_background,
                        "vatar",
                        "9",
                        "hindi",
                        "3h 15m 36s"
                    )
                )
                data.add(
                    itemsPractice(
                        com.qtonz.photos.R.drawable.ic_launcher_background,
                        "vatar",
                        "9",
                        "hindi",
                        "3h 15m 36s"
                    )
                )

                binding.rvImage.adapter = adapter
            }

            1 -> {
                val itemsList = listOf(
                    Items(
                        text = "Android",
                        mainImage = com.qtonz.photos.R.drawable.icon,
                        img1 = com.qtonz.photos.R.drawable.clip,
                        img2 = com.qtonz.photos.R.drawable.clip,
                        img3 = com.qtonz.photos.R.drawable.clip,
                        boolean = false
                    ), Items(
                        text = "Ios",
                        mainImage = com.qtonz.photos.R.drawable.icon,
                        img1 = com.qtonz.photos.R.drawable.clip,
                        img2 = com.qtonz.photos.R.drawable.clip,
                        img3 = com.qtonz.photos.R.drawable.clip,
                        boolean = false
                    ), Items(
                        text = "Flutter",
                        mainImage = com.qtonz.photos.R.drawable.icon,
                        img1 = com.qtonz.photos.R.drawable.clip,
                        img2 = com.qtonz.photos.R.drawable.clip,
                        img3 = com.qtonz.photos.R.drawable.clip,
                        boolean = false
                    )
                )
                binding.rvImage.layoutManager = LinearLayoutManager(requireContext())
                binding.rvImage.adapter = NestedViewPagerAdapter(itemsList)
            }

            2 -> {
                binding.rvImage.layoutManager = GridLayoutManager(requireContext(),3)
                courseList = ArrayList()
                courseList.add(CourseRVModal("Android Development", com.qtonz.photos.R.drawable.icon))
                courseList.add(CourseRVModal("C++ Development", com.qtonz.photos.R.drawable.icon))
                courseList.add(CourseRVModal("Java Development", com.qtonz.photos.R.drawable.clip))
                courseList.add(CourseRVModal("Python Development", com.qtonz.photos.R.drawable.icon))
                courseList.add(CourseRVModal("JavaScript Development", com.qtonz.photos.R.drawable.clip))
                binding.rvImage.adapter = GridViewPagerAdapter(courseList,requireContext())
            }
            3->{
                images = arrayListOf(
                    com.qtonz.photos.R.drawable.icon,
                    com.qtonz.photos.R.drawable.clip,
                    com.qtonz.photos.R.drawable.gallery_svg_bg,
                    com.qtonz.photos.R.drawable.clip,
                    com.qtonz.photos.R.drawable.icon,
                    com.qtonz.photos.R.drawable.clip
//                    com.qtonz.photos.R.drawable.icon,
//                    com.qtonz.photos.R.drawable.ic_launcher_background,
//                    com.qtonz.photos.R.drawable.icon,
//                    com.qtonz.photos.R.drawable.gallery_svg_bg
                )
                binding.rvImage.layoutManager =
                    StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
                binding.rvImage.adapter = StaggeredViewPagerAdapter(requireContext(),images)
            }
            else -> {
//                binding.rvImage.layoutManager = LinearLayoutManager(requireContext())
            }
        }

        return binding.root
    }

    companion object {
        private const val ARG_TAB_POSITION = "tab_position"
        fun newInstance(tabPosition: Int): ViewPagerFragment {
            val fragment = ViewPagerFragment()
            val args = Bundle()
            args.putInt(ARG_TAB_POSITION, tabPosition)
            fragment.arguments = args
            return fragment
        }
    }
}
