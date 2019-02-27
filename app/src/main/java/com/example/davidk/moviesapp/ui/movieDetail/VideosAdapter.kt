package com.example.davidk.moviesapp.ui.movieDetail

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.davidk.moviesapp.model.Video
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.davidk.moviesapp.R


class VideosAdapter(private val mContext: Context, private val trailerList: List<Video>) : RecyclerView.Adapter<VideosAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): VideosAdapter.MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.video_card, viewGroup, false)
        return MyViewHolder(view)

    }

    override fun onBindViewHolder(viewHolder: VideosAdapter.MyViewHolder, position: Int) {
        viewHolder.title.text = trailerList[position].name
        viewHolder.title.setOnClickListener {
                val videoId = trailerList[position].key
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=$videoId"))
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                intent.putExtra("VIDEO_ID", videoId)
                mContext.startActivity(intent)
        }
    }

    override fun getItemCount() = trailerList.size

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.title)
    }
}