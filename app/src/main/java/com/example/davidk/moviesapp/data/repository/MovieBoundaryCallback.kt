package com.example.davidk.moviesapp.data.repository

import android.arch.paging.PagedList
import android.util.Log
import com.example.davidk.moviesapp.model.Movie
import com.example.davidk.moviesapp.model.MoviesResponse
import com.example.davidk.moviesapp.data.database.MoviesDao
import com.example.davidk.moviesapp.data.network.MoviesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieBoundaryCallback(val moviesApi: MoviesApi, val moviesDao: MoviesDao) : PagedList.BoundaryCallback<Movie>() {
    // keep the last requested page.
    // When the request is successful, increment the page number.
    private var lastRequestedPage = 1

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false


    /**
     * Database returned 0 items. We should query the backend for more items.
     */
    override fun onZeroItemsLoaded() {
        Log.d("MovieBoundaryCallback", "onZeroItemsLoaded")
        requestAndSaveData()
    }

    /**
     * When all items in the database were loaded, we need to query the backend for more items.
     */
    override fun onItemAtEndLoaded(itemAtEnd: Movie) {
        Log.d("MovieBoundaryCallback", "onItemAtEndLoaded")
        requestAndSaveData()
    }

    private fun requestAndSaveData() {
        if (isRequestInProgress) return

        isRequestInProgress = true

        fetchNowPlayingMovies()
    }

    private fun fetchNowPlayingMovies() {
        val call: Call<MoviesResponse> = moviesApi.getNowPlayingMovies(lastRequestedPage.toString())
        call.enqueue(object : Callback<MoviesResponse> {
            override fun onFailure(call: Call<MoviesResponse>?, t: Throwable?) {
                isRequestInProgress = false
            }

            override fun onResponse(call: Call<MoviesResponse>?, response: Response<MoviesResponse>?) {
                lastRequestedPage++
                isRequestInProgress = false
                GlobalScope.launch {
                    withContext(this.coroutineContext) { response?.body()?.movies?.let { moviesDao.insertMovies(it) } }
                }
            }

        })
    }
}