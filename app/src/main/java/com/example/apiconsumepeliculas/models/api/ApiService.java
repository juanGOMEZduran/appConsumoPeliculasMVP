package com.example.apiconsumepeliculas.models.api;

import com.example.apiconsumepeliculas.models.model.MovieResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiService {
    private static final String BEARER_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4NDYxNmQ1YjZiYWM1MDQ0YmYwNGNhNDQ3MGJiYTdlOSIsIm5iZiI6MTc0NjU2MzI0Ny4yMTIsInN1YiI6IjY4MWE3MGFmYjY2YzgxNjc4ZmY2ZTZkMyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.uz7GVydzUjzmkvyCzIRpbRb2p4MtfiF6J6E6nfu0Tks";
    private static final String ACCEPT_HEADER = "application/json";
    private ApiInterface apiInterface;

    public ApiService() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public void discoverMovies(int page, final MovieCallback callback) {
        Call<MovieResponse> call = apiInterface.discoverMovies(
                "Bearer " + BEARER_TOKEN, // Solo este header de Authorization
                ACCEPT_HEADER,            // Solo este header de accept
                false,  // include_adult (query param)
                false,  // include_video (query param)
                "es",   // language (query param)
                page,   // page number (query param)
                "popularity.desc"  // sort_by (query param)
        );

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public interface MovieCallback {
        void onSuccess(MovieResponse response);
        void onError(String error);
    }
}