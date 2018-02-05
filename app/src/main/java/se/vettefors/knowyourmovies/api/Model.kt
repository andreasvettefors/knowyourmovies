package se.vettefors.knowyourmovies.api

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Model {
    data class Response(@SerializedName("page") val page: Int,
                        @SerializedName("total_results") val totalResults: Int,
                        @SerializedName("total_pages") val totalPages: Int,
                        @SerializedName("results") val movies: List<Movie>)

    data class Movie(@SerializedName("id") val id: Int,
                     @SerializedName("title") val title: String,
                     @SerializedName("vote_average") val voteAverage: Double,
                     @SerializedName("release_date") val releaseDate: String,
                     @SerializedName("poster_path") val poster: String,
                     @SerializedName("backdrop_path") val backdrop: String,
                     @SerializedName("overview") val overview: String) : Serializable
}