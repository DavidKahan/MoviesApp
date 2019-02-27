package com.example.davidk.moviesapp.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.example.davidk.moviesapp.util.Constants


@Entity(tableName = Constants.DataBase.MOVIES_TABLE_NAME)
data class Movie(
    @field:SerializedName("vote_average") val voteAverage: Double,
    @field:SerializedName("backdrop_path") var backdropPath: String,
    @PrimaryKey @ColumnInfo(name = Constants.DataBase.ID_COLUMN_NAME) @field:SerializedName("id") val id: Int,
    @field:SerializedName("title") val title: String,
    @field:SerializedName("popularity") val popularity: Double,
    @field:SerializedName("poster_path") var posterPath: String,
    @field:SerializedName("overview") val overview: String,
    @field:SerializedName("release_date") val releaseDate: String,
    @field:SerializedName("original_title") val originalTitle: String,
    @field:SerializedName("original_language") val originalLanguage: String,
    @field:SerializedName("vote_count") val voteCount: Int
)

data class MoviesResponse(
    @SerializedName("results") var movies: ArrayList<Movie>?,
    @SerializedName("page") var page: Int,
    @SerializedName("total_pages") var totalPages: Int,
    @SerializedName("total_results") var totalResults: Int
)

@Entity(tableName = Constants.DataBase.VIDEOS_TABLE_NAME)
data class Video(
    @PrimaryKey @ColumnInfo(name = Constants.DataBase.KEY_COLUMN_NAME) @SerializedName("key") var key: String,
    @SerializedName("name") var name: String?
)

data class VideosResponse (
    @SerializedName("id") var idTrailer: Int,
    @SerializedName("results") val results: List<Video>?
)



