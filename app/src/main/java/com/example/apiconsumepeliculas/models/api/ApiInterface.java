package com.example.apiconsumepeliculas.models.api;

import com.example.apiconsumepeliculas.models.model.MovieDetails;
import com.example.apiconsumepeliculas.models.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("discover/movie")
    Call<MovieResponse> discoverMovies(
            @Header("Authorization") String authorization,
            @Header("accept") String accept,
            @Query("include_adult") boolean includeAdult,
            @Query("include_video") boolean includeVideo,
            @Query("language") String language,
            @Query("page") int page,
            @Query("sort_by") String sortBy
    );

    @GET("search/movie")
    Call<MovieResponse> searchMovies(
            @Header("Authorization") String authorization,
            @Header("accept") String accept,
            @Query("query") String query,
            @Query("include_adult") boolean includeAdult,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("movie/{movie_id}")
    Call<MovieDetails> getMovieDetails(
            @Path("movie_id") int movieId,
            @Header("Authorization") String authorization,
            @Header("accept") String accept,
            @Query("language") String language
    );
}
