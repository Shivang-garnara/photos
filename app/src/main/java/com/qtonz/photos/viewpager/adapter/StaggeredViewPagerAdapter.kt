package com.qtonz.photos.viewpager.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.qtonz.photos.R


class StaggeredViewPagerAdapter(var context: Context, private var images: ArrayList<*>) :
    RecyclerView.Adapter<StaggeredViewPagerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)

        val viewHolder: ViewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val res = images[position] as Int
        holder.images.setImageResource(res)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var images: ImageView = view.findViewById<View>(R.id.imageView) as ImageView
    }
}
