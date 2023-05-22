package fr.epf.mm.cinemathome

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "5b6adc52282ae68909484fb3fcd7cd07"

interface APIInterface {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int
    ): Call<MovieResult>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int
    ): Call<MovieResult>

    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int
    ): Call<MovieResult>

}