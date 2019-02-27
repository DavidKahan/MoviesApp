package com.example.davidk.moviesapp.util

class Constants {

    object DataBase {
        const val DATA_BASE_NAME = "movies_db"
        const val MOVIES_TABLE_NAME = "movies_table"
        const val ID_COLUMN_NAME = "id"
        const val KEY_COLUMN_NAME = "key"
        const val VIDEOS_TABLE_NAME = "videos_table"
    }


    companion object {
        const val MOVIES_DD_REQUEST_BASE_URL = "https://api.themoviedb.org/3/movie/"
        const val BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w185/"
        const val API_KEY_VALUE = "9faa3b37c46f7229a4fdf25198516d07"
    }

}