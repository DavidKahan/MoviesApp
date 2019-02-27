package com.example.davidk.moviesapp.ui.movieDetail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.davidk.moviesapp.util.Constants
import com.example.davidk.moviesapp.R
import com.example.davidk.moviesapp.model.Movie
import com.example.davidk.moviesapp.model.Video
import com.example.davidk.moviesapp.data.repository.MoviesRepository
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.movie_detail.*
import android.support.v7.widget.LinearLayoutManager


/**
 * A fragment representing a single Movie detail screen.
 * This fragment is either contained in a [MovieListActivity]
 * in two-pane mode (on tablets) or a [MovieDetailActivity]
 * on handsets.
 */
class MovieDetailFragment : Fragment() {

    private lateinit var movieDetailsViewModel: MovieDetailsViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val repo = MoviesRepository.getInstance(activity!!.application)
        movieDetailsViewModel = ViewModelProviders.of(this, MovieDetailsViewModelFactory(activity!!.application, repo))
            .get(MovieDetailsViewModel::class.java)

        arguments?.let {
            if (it.containsKey(MOVIE_ID_KEY)) {
                movieDetailsViewModel.getMovieDetailsObservable.observe(this, Observer<Movie> {
                    activity?.toolbar_layout?.title = it!!.title
                    title.text = it!!.title
                    Glide.with(activity!!).load(Constants.BASE_IMAGE_URL + it.posterPath).into(poster)
                    release_date.text = it.releaseDate
                    vote_average.text = it.voteAverage.toString() + "/10"
                    overview.text = it.overview

                })
                movieDetailsViewModel.getMovieVideosObservable.observe(this, Observer<List<Video>> {
                    if (it != null){
                        val layoutManager = LinearLayoutManager(activity?.applicationContext)
                        videos_recycler.layoutManager = layoutManager
                        videos_recycler.adapter = VideosAdapter(activity!!, it!!)
                    }
                })

                movieDetailsViewModel.fetchMovieDetailsById(it.getInt(MOVIE_ID_KEY))
            }
        }


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.movie_detail, container, false)
        return rootView
    }

    companion object {
        const val MOVIE_ID_KEY = "movieIdKey"
    }
}
