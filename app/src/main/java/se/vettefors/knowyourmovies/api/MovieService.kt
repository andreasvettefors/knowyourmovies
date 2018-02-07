package se.vettefors.knowyourmovies.api

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import se.vettefors.knowyourmovies.ext.Constants

interface MovieService {

    @GET("discover/movie")
    fun getMovies(@Query("api_key") apiKey: String = Constants.API_KEY,
                  @Query("sort_by") sortBy: String = Constants.DEFAULT_SORT,
                  @Query("vote_count.gte") voteCount: Int = 8000,
                  @Query("page") page: Int = 1)
            : Observable<Model.Response>

    @GET("movie/{movieId}")
    fun getMovie(@Path("movieId") movieId : Int,
                    @Query("api_key") apiKey : String = Constants.API_KEY,
                    @Query("append_to_response") append : String = "videos")
    : Observable<Model.SingleMovie>


    companion object {
        fun create(): MovieService {
            return Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(MovieService::class.java)
        }
    }
}