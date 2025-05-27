package com.example.apiconsumepeliculas.Interfaces;

import com.example.apiconsumepeliculas.models.model.MovieDetails;

public interface MovieDetailsContract {
    interface View {
        void showMovieDetails(MovieDetails movieDetails);
        void showError(String message);
        void showLoading();
        void hideLoading();
    }

    interface Presenter {
        void loadMovieDetails(int movieId);
        void onDestroy();
    }
}
