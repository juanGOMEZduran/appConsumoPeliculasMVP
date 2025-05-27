package com.example.apiconsumepeliculas.presenter;
import com.example.apiconsumepeliculas.Interfaces.MovieDetailsContract;
import com.example.apiconsumepeliculas.models.api.ApiClient;
import com.example.apiconsumepeliculas.models.api.ApiInterface;
import com.example.apiconsumepeliculas.models.model.MovieDetails;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsPresenter implements MovieDetailsContract.Presenter {
    private MovieDetailsContract.View view;
    private ApiInterface apiService;
    private static final String BEARER_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4NDYxNmQ1YjZiYWM1MDQ0YmYwNGNhNDQ3MGJiYTdlOSIsIm5iZiI6MTc0NjU2MzI0Ny4yMTIsInN1YiI6IjY4MWE3MGFmYjY2YzgxNjc4ZmY2ZTZkMyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.uz7GVydzUjzmkvyCzIRpbRb2p4MtfiF6J6E6nfu0Tks";
    private static final String ACCEPT_HEADER = "application/json";

    public MovieDetailsPresenter(MovieDetailsContract.View view) {
        this.view = view;
        this.apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    @Override
    public void loadMovieDetails(int movieId) {

        System.out.println("desde el presenter el  id es: "+movieId);
        view.showLoading();

        Call<MovieDetails> call = apiService.getMovieDetails(
                movieId,
                BEARER_TOKEN,
                ACCEPT_HEADER,
                "es"
        );
        System.out.println("desde el presenter el  2id es: "+movieId);

        call.enqueue(new Callback<MovieDetails>() {

            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                view.hideLoading();
                System.out.println("desde el presenter el  3 id es: "+movieId);
                if (response.isSuccessful() && response.body() != null) {
                    System.out.println("desde el presenter el   4 id es: "+movieId);
                    view.showMovieDetails(response.body());
                } else {
                    System.out.println("desde el presenter 4 el  id es: "+movieId);
                    view.showError("Error al obtener detalles: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {
                view.hideLoading();
                view.showError(t.getMessage());
            }
        });
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}