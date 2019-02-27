package com.example.davidk.moviesapp.data.repository

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.example.davidk.moviesapp.util.Constants
import com.example.davidk.moviesapp.data.database.MoviesDao
import com.example.davidk.moviesapp.data.database.MoviesDatabase
import com.example.davidk.moviesapp.data.network.MoviesApi
import com.example.davidk.moviesapp.model.Movie
import com.example.davidk.moviesapp.model.Video
import com.example.davidk.moviesapp.model.VideosResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MoviesRepository(val moviesApi: MoviesApi, val moviesDao: MoviesDao) {

    private val movieResponseData: MutableLiveData<Movie> = MutableLiveData()
    fun getMovieObservable(): LiveData<Movie> = movieResponseData

    private val movieVideosData: MutableLiveData<List<Video>> = MutableLiveData()
    fun getMovieVideosObservable(): LiveData<List<Video>> = movieVideosData

    private lateinit var movie: Movie

    fun getNowPlayingMovies(): LiveData<PagedList<Movie>> {
        val dataSourceFactory = moviesDao.getAllMovies()
        // Construct the boundary callback
        val boundaryCallback = MovieBoundaryCallback(moviesApi, moviesDao)
        // Get the paged list
        val data = LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback)
            .build()
        return data
    }

    fun getMovieDetailsById(id : Int) {
        GlobalScope.launch {
            withContext(this.coroutineContext) { movie = moviesDao.getMovieById(id) }
            movieResponseData.postValue(movie)
        }
    }

    fun getMovieVideosById(id: Int) {
        val call: Call<VideosResponse> = moviesApi.fetchVideos(id.toString())
        call.enqueue(object : Callback<VideosResponse> {
            override fun onFailure(call: Call<VideosResponse>?, t: Throwable?) {
                movieVideosData.postValue(null)
            }

            override fun onResponse(call: Call<VideosResponse>?, response: Response<VideosResponse>?) {
                movieVideosData.postValue(response?.body()?.results)
                GlobalScope.launch {
                    withContext(this.coroutineContext) { response?.body()?.results?.let { moviesDao.insertVideos(it) } }
                }
            }

        })
    }


    companion object {
        private const val DATABASE_PAGE_SIZE = 20

        private var INSTANCE: MoviesRepository? = null

        fun getInstance(application: Application): MoviesRepository {
            if (INSTANCE == null) {
                val converter = GsonConverterFactory.create()

                val retrofit = Retrofit.Builder()
                    .baseUrl(Constants.MOVIES_DD_REQUEST_BASE_URL)
                    .addConverterFactory(converter)
                    .build()

                val moviesApi = retrofit.create(MoviesApi::class.java)

                val moviesDatabase : MoviesDatabase = MoviesDatabase.getInstance(application)
                val moviesDao : MoviesDao = moviesDatabase.moviesDao

                val repo = MoviesRepository(moviesApi, moviesDao)

                INSTANCE = repo
            }

            return INSTANCE!!
        }

    }

}