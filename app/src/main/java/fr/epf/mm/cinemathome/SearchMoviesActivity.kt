package fr.epf.mm.cinemathome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop

const val MOVIE_SEARCH = "movie_search"

class SearchMoviesActivity : AppCompatActivity() {
    val apiKey = "5b6adc52282ae68909484fb3fcd7cd07"
    val language = "en-US"

    private lateinit var searchMovies: RecyclerView
    private lateinit var searchMoviesAdapter: MovieAdapter
    private lateinit var searchMoviesLayoutMgr: LinearLayoutManager
    private var searchMoviesPage = 1
    private lateinit var search_string: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_movies)
        searchMovies = findViewById(R.id.search_movies)
        searchMoviesLayoutMgr = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        searchMovies.layoutManager = searchMoviesLayoutMgr
        searchMoviesAdapter = MovieAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        searchMovies.adapter = searchMoviesAdapter

        val extras = intent.extras


        if (extras != null) {
            search_string = extras.getString(MOVIE_SEARCH).toString()
            val searched = findViewById<TextView>(R.id.search_movies_textview)
            searched.text = "\""+search_string+"\""
            getSearchMovies()
        } else {
            finish()
        }
    }


    private fun getSearchMovies() {
        MovieRepository.getSearchMovies(
            searchMoviesPage,
            search_string,
            ::onSearchMoviesFetched,
            ::onError
        )
    }
    private fun attachSearchMoviessOnScrollListener() {
        searchMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = searchMoviesLayoutMgr.itemCount
                val visibleItemCount = searchMoviesLayoutMgr.childCount
                val firstVisibleItem = searchMoviesLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    searchMovies.removeOnScrollListener(this)
                    searchMoviesPage++

                }
            }
        })
    }

    private fun onSearchMoviesFetched(movies: List<Movie>) {
        searchMoviesAdapter.appendMovies(movies)
        attachSearchMoviessOnScrollListener()
    }


    private fun showMovieDetails(movie: Movie) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra(MOVIE_BACKDROP, movie.backdropPath)
        intent.putExtra(MOVIE_POSTER, movie.posterPath)
        intent.putExtra(MOVIE_TITLE, movie.title)
        intent.putExtra(MOVIE_RATING, movie.rating)
        intent.putExtra(MOVIE_RELEASE_DATE, movie.releaseDate)
        intent.putExtra(MOVIE_OVERVIEW, movie.overview)
        intent.putExtra(MOVIE_ID, movie.id)
        startActivity(intent)
    }

    private fun onError() {
        Toast.makeText(this, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu (menu: Menu?) : Boolean{
        menuInflater.inflate(R.menu.movie_details_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_qrcode -> {
                val intent = Intent(this, ScannerActivity::class.java)
                startActivity(intent)
            }

            R.id.action_favorite -> {
                val intent = Intent(this, FavoriteMoviesActivity::class.java)
                startActivity(intent)
            }

            R.id.action_home -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }



}