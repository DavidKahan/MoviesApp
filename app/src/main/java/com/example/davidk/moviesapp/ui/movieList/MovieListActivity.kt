package com.example.davidk.moviesapp.ui.movieList

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.davidk.moviesapp.R
import com.example.davidk.moviesapp.model.Movie
import com.example.davidk.moviesapp.data.repository.MoviesRepository
import kotlinx.android.synthetic.main.activity_movie_list.*
import kotlinx.android.synthetic.main.movie_list.*

class MovieListActivity : AppCompatActivity() {

    private lateinit var moviesViewModel : MoviesViewModel
    private lateinit var adapter : MoviesAdapter

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)
        setupRecyclerView(movie_list, twoPane)
        setSupportActionBar(toolbar)
        toolbar.title = title

        if (movie_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        val repo = MoviesRepository.getInstance(application)
        moviesViewModel = ViewModelProviders.of(this, MoviesViewModelFactory(application, repo)).get(MoviesViewModel::class.java)
        moviesViewModel.movieList.observe(this, Observer<PagedList<Movie>> {
            if (!it.isNullOrEmpty()){
                frameLayout.visibility = View.VISIBLE
                no_result.visibility = View.GONE
                adapter.submitList(it)
            } else {
                frameLayout.visibility = View.GONE
                no_result.visibility = View.VISIBLE
            }
        })
    }

    private fun setupRecyclerView(recyclerView: RecyclerView, twoPane: Boolean) {
        if (twoPane){
            recyclerView.layoutManager = GridLayoutManager(this, 3)
        } else {
            recyclerView.layoutManager = GridLayoutManager(this, 2)
        }
        adapter = MoviesAdapter(
            this,
            this.twoPane
        )
        movie_list.adapter = adapter

    }

}
