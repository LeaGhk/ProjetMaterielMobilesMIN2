package fr.epf.mm.cinemathome

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.epf.mm.cinemathome.MovieRepository.getMovie

class FavoriteMoviesActivity: AppCompatActivity() {
    private lateinit var favoriteMovies: RecyclerView
    private lateinit var favoriteMoviesAdapter: MovieAdapter
    private lateinit var favoriteMoviesLayoutMgr: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_movies)

        val favoritesSet = FavoritesManager.getFavorites(this)
        val favoriteMoviesList = mutableListOf<Movie>()
        var pendingRequests = favoritesSet.size



        for (movieId in favoritesSet) {
            val ID = movieId.toInt()
            getMovie(ID) { movie, error ->
                pendingRequests--
                if (error != null) {

                } else {
                    if (movie != null) {
                        favoriteMoviesList.add(movie)
                    }
                }
                if (pendingRequests == 0) {
                    displayFavoriteMovies(favoriteMoviesList)
                }
            }
        }

    }

    private fun displayFavoriteMovies(favoriteMoviesList: MutableList<Movie>){
        favoriteMovies = findViewById(R.id.favorite_movies)
        favoriteMoviesLayoutMgr = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        favoriteMovies.layoutManager = favoriteMoviesLayoutMgr
        favoriteMoviesAdapter = MovieAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        favoriteMovies.adapter = favoriteMoviesAdapter
        onPopularMovieFetched(favoriteMoviesList)
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


    private fun onPopularMovieFetched(movies: List<Movie>) {
        favoriteMoviesAdapter.appendMovies(movies)
        attachPopularMoviesOnScrollListener()
    }
    private fun attachPopularMoviesOnScrollListener() {
        favoriteMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = favoriteMoviesLayoutMgr.itemCount
                val visibleItemCount = favoriteMoviesLayoutMgr.childCount
                val firstVisibleItem = favoriteMoviesLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    favoriteMovies.removeOnScrollListener(this)
                }
            }
        })
    }

    override fun onCreateOptionsMenu (menu: Menu?) : Boolean{
        menuInflater.inflate(R.menu.movie_details_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_home -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.action_qrcode -> {
                val intent = Intent(this, ScannerActivity::class.java)
                startActivity(intent)
            }

            R.id.action_favorite -> {
                val intent = Intent(this, FavoriteMoviesActivity::class.java)
                startActivity(intent)
            }

        }
        return super.onOptionsItemSelected(item)
    }
}