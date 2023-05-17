package fr.epf.mm.cinemathome

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.epf.mm.cinemathome.APITMDB.Api
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    val page = 1
    val apiKey = "5b6adc52282ae68909484fb3fcd7cd07"
    val language = "en-US"
    val category = "popular"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MovieRepository.getPopularMovies()

    /*    val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(Api::class.java)
        Call<MovieResult> Call = service.listOfMovies(category, apiKey, language, page)*/
    }



}