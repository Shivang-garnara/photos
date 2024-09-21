package com.qtonz.photos.viewpager.adapter



import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.qtonz.photos.R
import com.qtonz.photos.viewpager.data.itemsPractice

class SimpleViewPagerAdapter(private val mList: List<itemsPractice>) :
    RecyclerView.Adapter<SimpleViewPagerAdapter.PracticeViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PracticeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_practice_items, parent, false)

        return PracticeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: PracticeViewHolder, position: Int) {
        val itemsPractice = mList[position]
        holder.movie.setImageResource(itemsPractice.img)
        holder.moviename.text = itemsPractice.moviename
        holder.rating.text = itemsPractice.rating
        holder.la.text = itemsPractice.language
        holder.time.text = itemsPractice.time
        holder.log()
    }

    class PracticeViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val movie: ImageView = itemView.findViewById(R.id.imgMovie)
        val moviename: TextView = itemView.findViewById(R.id.tvMovieName)
        val rating: TextView = itemView.findViewById(R.id.tvRate)
        val la: TextView = itemView.findViewById(R.id.tvLanguage)
        val time: TextView = itemView.findViewById(R.id.tvMovieTime)
        fun log() {
            Log.d("test", "$movie, $moviename")
        }
    }
}