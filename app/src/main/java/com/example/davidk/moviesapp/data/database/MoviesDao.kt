package com.example.davidk.moviesapp.data.database

import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.davidk.moviesapp.util.Constants
import com.example.davidk.moviesapp.model.Movie
import com.example.davidk.moviesapp.model.Video

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovies(movies: List<Movie>)

    //ORDER BY key ASC
    @Query("SELECT * FROM ${Constants.DataBase.MOVIES_TABLE_NAME}")
    fun getAllMovies(): DataSource.Factory<Int, Movie>

    @Query("SELECT * FROM ${Constants.DataBase.MOVIES_TABLE_NAME} WHERE id = :id")
    fun getMovieById(id : Int): Movie

    @Query("DELETE FROM ${Constants.DataBase.MOVIES_TABLE_NAME}")
    fun removeAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertVideos(videos: List<Video>)

    @Query("SELECT * FROM ${Constants.DataBase.VIDEOS_TABLE_NAME} WHERE `key` = :key")
    fun getVideoById(key : String): Video
}