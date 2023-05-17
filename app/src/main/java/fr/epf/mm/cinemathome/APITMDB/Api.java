package fr.epf.mm.cinemathome.APITMDB;

import fr.epf.mm.cinemathome.MovieResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {
    @GET("/3/movie/{category}")
/*
    https://api.themoviedb.org/3/movie/550?api_key=5b6adc52282ae68909484fb3fcd7cd07
*/

    Call<MovieResult> getMovies(
        @Path("category") String category,
        @Query("api_key") String apiKey,
        @Query("language") String language,
        @Query("page") int page
    );
}
