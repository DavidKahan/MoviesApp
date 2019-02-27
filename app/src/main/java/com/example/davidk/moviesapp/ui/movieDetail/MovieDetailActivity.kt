package com.example.davidk.moviesapp.ui.movieDetail

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.davidk.moviesapp.R
import com.example.davidk.moviesapp.ui.movieList.MovieListActivity
import kotlinx.android.synthetic.main.activity_movie_detail.*

/**
 * An activity representing a single Movie detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [MovieListActivity].
 */
class MovieDetailActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        setSupportActionBar(detail_toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            val fragment = MovieDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(
                        MovieDetailFragment.MOVIE_ID_KEY,
                        intent.getIntExtra(MovieDetailFragment.MOVIE_ID_KEY, 0)
                    )
                }
            }

            supportFragmentManager.beginTransaction()
                .add(R.id.movie_detail_container, fragment)
                .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                supportFinishAfterTransition()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}
