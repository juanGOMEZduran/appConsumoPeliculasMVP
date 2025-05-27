package com.example.apiconsumepeliculas.Interfaces;

import com.example.apiconsumepeliculas.models.model.Movie;

import java.util.List;

public interface MovieContract {
    interface View {
        void showMovies(List<Movie> movies);

        void showPages(int pages);
        void showError(String message);
        void showLoading();
        void hideLoading();
    }

    interface Presenter {
        void loadMovies(int page);

        void searchMovies(String query, int page);
        void onDestroy();
    }
}