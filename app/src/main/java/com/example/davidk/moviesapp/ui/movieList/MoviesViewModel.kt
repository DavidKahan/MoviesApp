package com.example.davidk.moviesapp.ui.movieList

import android.app.Application
import android.arch.lifecycle.*
import android.arch.paging.PagedList
import com.example.davidk.moviesapp.model.Movie
import com.example.davidk.moviesapp.data.repository.MoviesRepository


class MoviesViewModel(application: Application, moviesRepository : MoviesRepository) : AndroidViewModel(application){
    var movieList: LiveData<PagedList<Movie>> = moviesRepository.getNowPlayingMovies()
}

class MoviesViewModelFactory( val application: Application, val moviesRepository : MoviesRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return MoviesViewModel(application, moviesRepository) as T
    }
}