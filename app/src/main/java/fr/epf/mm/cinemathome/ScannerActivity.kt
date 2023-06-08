package fr.epf.mm.cinemathome

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView
import android.Manifest
import android.content.Intent
import fr.epf.mm.cinemathome.MovieRepository.getMovie

class ScannerActivity : Activity(), ZXingScannerView.ResultHandler {
    private lateinit var mScannerView: ZXingScannerView

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        // Demander l'autorisation de la caméra si nécessaire
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST
            )
        } else {
            mScannerView = ZXingScannerView(this)
            setContentView(mScannerView)
        }




    }



    override fun onResume() {
        super.onResume()
        mScannerView.setResultHandler(this)
        mScannerView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        mScannerView.stopCamera()
    }

    override fun handleResult(result: Result?) {
        // Traitement du résultat du scan
        if (result != null) {
            val scannedText = result.text
            Toast.makeText(this, scannedText, Toast.LENGTH_SHORT).show()

            val movieId = scannedText.toInt()

            getMovie(movieId) { movie, error ->
                if (error != null) {
                    // Gérez l'erreur
                } else {
                    if (movie != null) {
                        showMovieDetails(movie)
                    }
                }
            }

        }





        // Redémarrage du scanner pour une autre analyse
        mScannerView.resumeCameraPreview(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // La permission de la caméra est accordée, redémarre la caméra
                mScannerView.setResultHandler(this)
                mScannerView.startCamera()
            } else {
                // La permission de la caméra est refusée, affiche un message d'erreur
                Toast.makeText(
                    this,
                    "Permission de la caméra refusée. Vous ne pouvez pas scanner de codes QR.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    companion object {
        private const val CAMERA_PERMISSION_REQUEST = 123
    }

    fun showMovieDetails(movie: Movie) {
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

}