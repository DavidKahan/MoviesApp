package com.example.davidk.moviesapp.ui.movieList

import android.arch.paging.PagedListAdapter
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.davidk.moviesapp.util.Constants
import com.example.davidk.moviesapp.R
import com.example.davidk.moviesapp.model.Movie
import com.example.davidk.moviesapp.ui.movieDetail.MovieDetailActivity
import com.example.davidk.moviesapp.ui.movieDetail.MovieDetailFragment
import android.support.v4.app.ActivityOptionsCompat


class MoviesAdapter (private val activity: AppCompatActivity, private val twoPane: Boolean) : PagedListAdapter<Movie, MoviesAdapter.MovieHolder>(MOVIE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieHolder(view)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val movieItem = getItem(position)
        if (movieItem != null){
            Glide.with(activity).load(Constants.BASE_IMAGE_URL + movieItem.posterPath).into(holder.poster)

            holder.itemView.setOnClickListener {
                if (twoPane) {
                    val fragment = MovieDetailFragment().apply {
                        arguments = Bundle().apply {
                            putInt(MovieDetailFragment.MOVIE_ID_KEY, movieItem.id)
                        }
                    }
                    activity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.movie_detail_container, fragment)
                        .commit()
                } else {
                    val intent = Intent(activity, MovieDetailActivity::class.java).apply {
                        putExtra(MovieDetailFragment.MOVIE_ID_KEY, movieItem.id)
                    }
                    val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, holder.poster, "poster")
                    activity.startActivity(intent, options.toBundle())
                }
            }
        }

    }

    class MovieHolder(movieItem: View) : RecyclerView.ViewHolder(movieItem) {
        var poster: ImageView = movieItem.findViewById(R.id.poster)
    }

    companion object {
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == oldItem.id


            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem

        }
    }
}