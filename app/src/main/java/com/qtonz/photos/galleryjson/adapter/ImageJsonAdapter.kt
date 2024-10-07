//package com.qtonz.photos.galleryjson.adapter
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.qtonz.photos.databinding.ItemImageBinding
//
//class ImageJsonAdapter(private val onItemClick: (String,Int) -> Unit) : ListAdapter<String, ImageJsonAdapter.ImageViewHolder>(
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



package com.qtonz.photos.galleryjson.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qtonz.photos.databinding.ItemImageBinding

class ImageJsonAdapter(
    val imageList: MutableList<String>,  // Manually pass the image list
    private val onItemClick: (String, Int) -> Unit
) : RecyclerView.Adapter<ImageJsonAdapter.ImageViewHolder>() {

    // Create new ViewHolder for each item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    // Bind data to ViewHolder at each position
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imagePath = imageList[position]
        Glide.with(holder.itemView.context)
            .load(imagePath)
            .into(holder.binding.imgRecyclerView)

        holder.itemView.setOnClickListener {
            onItemClick(imagePath, position)
        }
    }

    // Returns the size of the image list
    override fun getItemCount(): Int {
        return imageList.size
    }

    // Custom ViewHolder class using View Binding
    class ImageViewHolder(val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root)

    // Add methods to update data in the adapter
    fun updateImageList(newImages: List<String>) {
        imageList.clear()
        imageList.addAll(newImages)
        notifyDataSetChanged()  // Notify RecyclerView of data change
    }

    fun addImage(imagePath: String) {
        imageList.add(imagePath)
        notifyItemInserted(imageList.size - 1)  // Notify RecyclerView of new item insertion
    }
}
