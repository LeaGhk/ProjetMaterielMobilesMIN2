package fr.epf.mm.cinemathome

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    val apiKey = "5b6adc52282ae68909484fb3fcd7cd07"
    val language = "en-US"

    private lateinit var popularMovies: RecyclerView
    private lateinit var popularMoviesAdapter: MovieAdapter

    lateinit var test : RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        test = findViewById(R.id.test_recyclerview)
        test.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)



        popularMovies = findViewById(R.id.popular_movies)
        popularMovies.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        popularMoviesAdapter = MovieAdapter(listOf())
        popularMovies.adapter = popularMoviesAdapter

        MovieRepository.getPopularMovies(
            onSuccess = ::onPopularMovieFetched,
            onError = ::onError
        )

    }

    private fun onPopularMovieFetched(movies: List<Movie>) {
        popularMoviesAdapter.updateMovies(movies)    }

    private fun onError() {
        Toast.makeText(this, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }

}