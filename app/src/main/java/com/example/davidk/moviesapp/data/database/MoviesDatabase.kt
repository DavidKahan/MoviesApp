package com.example.davidk.moviesapp.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.davidk.moviesapp.util.Constants
import com.example.davidk.moviesapp.model.Movie
import com.example.davidk.moviesapp.model.Video

@Database(entities = [Movie::class, Video::class] , version = 1, exportSchema = false)
abstract class MoviesDatabase : RoomDatabase() {

    abstract val moviesDao: MoviesDao


    companion object {
        private var INSTANCE: MoviesDatabase? = null

        fun getInstance(context: Context): MoviesDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                    MoviesDatabase::class.java, Constants.DataBase.DATA_BASE_NAME)
                    .build()
            }

            return INSTANCE!!
        }

    }
}