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
                     @SerializedName("backdrop_path") val backdrop: String)

    data class SingleMovie(@SerializedName("id") val id: Int,
                           @SerializedName("title") val title: String,
                           @SerializedName("tagline") val tagLine: String,
                           @SerializedName("backdrop_path") val backdrop: String,
                           @SerializedName("poster_path") val poster: String,
                           @SerializedName("overview") val overview: String,
                           @SerializedName("genres") val genres: List<Genre>,
                           @SerializedName("homepage") val homepage: String,
                           @SerializedName("release_date") val releaseDate: String,
                           @SerializedName("vote_average") val voteAverage: Double,
                           @SerializedName("vote_count") val voteCount: Int,
                           @SerializedName("videos") val videos: Results)

    data class Genre(@SerializedName("id") val id: Int,
                     @SerializedName("name") val name: String)

    data class Results(@SerializedName("results") val videos: List<Video>)

    data class Video(@SerializedName("key") val linkToVideo: String,
                     @SerializedName("name") val name: String,
                     @SerializedName("site") val site: String,
                     @SerializedName("type") val type: String)
}