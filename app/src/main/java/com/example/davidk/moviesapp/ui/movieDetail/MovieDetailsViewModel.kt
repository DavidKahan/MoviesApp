package com.example.davidk.moviesapp.ui.movieDetail

import android.app.Application
import android.arch.lifecycle.*
import com.example.davidk.moviesapp.model.Movie
import com.example.davidk.moviesapp.model.Video
import com.example.davidk.moviesapp.data.repository.MoviesRepository

class MovieDetailsViewModel (application: Application, moviesRepository : MoviesRepository) : AndroidViewModel(application){
    private var repository : MoviesRepository = moviesRepository

    var getMovieDetailsObservable : LiveData<Movie> = repository.getMovieObservable()
    var getMovieVideosObservable : LiveData<List<Video>> = repository.getMovieVideosObservable()

    fun fetchMovieDetailsById(id : Int) {
        repository.getMovieDetailsById(id)
        fetchMovieVideosById(id)
    }

    private fun fetchMovieVideosById(id : Int) {
        repository.getMovieVideosById(id)
    }
}

class MovieDetailsViewModelFactory(val application: Application, val moviesRepository : MoviesRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return MovieDetailsViewModel(application, moviesRepository) as T
    }
}