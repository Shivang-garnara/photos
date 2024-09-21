//package com.qtonz.photos.gallery.adapter
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.qtonz.photos.databinding.ItemImageBinding
//
//class ImageAdapter(private val onItemClick: (String,Int) -> Unit) : ListAdapter<String, ImageAdapter.ImageViewHolder>(
//    DiffCallback()
//) {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
//        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ImageViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
//        val imagePath = getItem(position)
//        Glide.with(holder.itemView.context)
//            .load(imagePath)
//            .into(holder.binding.imgRecyclerView)
//        holder.itemView.setOnClickListener {
//            onItemClick(imagePath,position)
//        }
//    }
//
//    class ImageViewHolder(val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root)
//
//    class DiffCallback : DiffUtil.ItemCallback<String>() {
//        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
//            return oldItem == newItem
//        }
//
//        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
//            return oldItem == newItem
//        }
//    }
//}


package com.qtonz.photos.gallery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qtonz.photos.databinding.ItemImageBinding

class ImageAdapter(
    private val imageList: MutableList<String>,
    private val onItemClick: (String, Int) -> Unit
) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imagePath = imageList[position]
        Glide.with(holder.itemView.context)
            .load(imagePath)
            .into(holder.binding.imgRecyclerView)

        holder.itemView.setOnClickListener {
            onItemClick(imagePath, position)
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    fun updateImages(newImages: List<String>) {
        imageList.clear()
        imageList.addAll(newImages)
        notifyDataSetChanged()  // Use more specific notify methods for better performance
    }

    fun addImage(imagePath: String) {
        imageList.add(imagePath)
        notifyItemInserted(imageList.size - 1)
    }

    fun removeImage(position: Int) {
        if (position < imageList.size) {
            imageList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    class ImageViewHolder(val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root)

    fun getImageList(): List<String> {
        return imageList
    }

}
