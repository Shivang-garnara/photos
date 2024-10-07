package com.qtonz.photos.galleryjson.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qtonz.photos.databinding.ItemImageDetailBinding

class DetailsViewPagerJsonAdapter(
    private val imagePaths: List<String>,
    private val onImageOpened: (String) -> Unit
) : RecyclerView.Adapter<DetailsViewPagerJsonAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemImageDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imagePath = imagePaths[position]
        Glide.with(holder.itemView.context)
            .load(imagePath)
            .into(holder.binding.imgImage)

        onImageOpened(imagePath)

        holder.binding.tvUri.text = imagePath
    }

    override fun getItemCount(): Int = imagePaths.size

    class ImageViewHolder(val binding: ItemImageDetailBinding) : RecyclerView.ViewHolder(binding.root)
}