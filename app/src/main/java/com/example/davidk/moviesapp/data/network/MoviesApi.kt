package com.example.davidk.moviesapp.data.network

import com.example.davidk.moviesapp.util.Constants
import com.example.davidk.moviesapp.model.MoviesResponse
import com.example.davidk.moviesapp.model.VideosResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MoviesApi {

    //https://api.themoviedb.org/3/movie/now_playing?api_key=<<api_key>>&language=en-US&page=1
    @GET("now_playing?api_key=${Constants.API_KEY_VALUE}")
    fun getNowPlayingMovies(@Query("page") page: String): Call<MoviesResponse>

    // Video trailers, clips, etc
    @GET("{key}/videos?api_key=${Constants.API_KEY_VALUE}")
    fun fetchVideos(@Path("key") movieId: String): Call<VideosResponse>
}