package com.example.apiconsumepeliculas.presenter;
import com.example.apiconsumepeliculas.Interfaces.MovieContract;
import com.example.apiconsumepeliculas.models.api.ApiClient;
import com.example.apiconsumepeliculas.models.api.ApiInterface;
import com.example.apiconsumepeliculas.models.model.MovieResponse;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviePresenter implements MovieContract.Presenter {
    private MovieContract.View view;
    private ApiInterface apiService;
    private static final String BEARER_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4NDYxNmQ1YjZiYWM1MDQ0YmYwNGNhNDQ3MGJiYTdlOSIsIm5iZiI6MTc0NjU2MzI0Ny4yMTIsInN1YiI6IjY4MWE3MGFmYjY2YzgxNjc4ZmY2ZTZkMyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.uz7GVydzUjzmkvyCzIRpbRb2p4MtfiF6J6E6nfu0Tks";
    private static final String ACCEPT_HEADER = "application/json";

    public MoviePresenter(MovieContract.View view) {
        this.view = view;
        this.apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    @Override
    public void loadMovies(int page) {
        view.showLoading();

        Call<MovieResponse> call = apiService.discoverMovies(
                BEARER_TOKEN,
                ACCEPT_HEADER,
                false,  // include_adult
                false,  // include_video
                "es",   // language
                page,   // page number
                "popularity.desc"  // sort_by
        );

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                view.hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    view.showMovies(response.body().getResults());
                    view.showPages(response.body().getTotalPages());
                } else {
                    view.showError("Error al obtener datos: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                view.hideLoading();
                view.showError(t.getMessage());
            }
        });
    }

    @Override
    public  void searchMovies(String query, int page){
        view.showLoading();

        Call<MovieResponse> call = apiService.searchMovies(
                BEARER_TOKEN,
                ACCEPT_HEADER,
                query,
                false,  // include_adult
                "es",   // language
                page    // page number
        );

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                view.hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    view.showMovies(response.body().getResults());
                    view.showPages(response.body().getTotalPages());
                } else {
                    view.showError("Error al buscar películas: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
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
