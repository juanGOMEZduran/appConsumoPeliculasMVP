package com.example.apiconsumepeliculas.View.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apiconsumepeliculas.R;
import com.example.apiconsumepeliculas.models.model.Movie;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    private List<Movie> movies;
    private OnMovieClickListener onMovieClickListener;
    public interface OnMovieClickListener {
        void onMovieClick(Movie movie);
    }
    public MoviesAdapter(List<Movie> movies, OnMovieClickListener listener) {
        this.movies = movies;
        this.onMovieClickListener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);

        holder.title.setText(movie.getTitle());
        holder.overview.setText(movie.getOverview());

        String id = String.valueOf(movie.getId());
        holder.idpelicula.setText(id);
        holder.rating.setText(String.format("⭐ %.1f", movie.getVoteAverage()));

        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath())
                .into(holder.poster);

        // Añadir click listener al item
        holder.itemView.setOnClickListener(v -> {
            if (onMovieClickListener != null) {
                onMovieClickListener.onMovieClick(movie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies != null ? movies.size() : 0;
    }

    public void updateMovies(List<Movie> newMovies) {
        movies = newMovies;
        notifyDataSetChanged();
    }



    static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView title, overview, rating, idpelicula;
        ImageView poster;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            idpelicula=itemView.findViewById(R.id.idpelicula);
            title = itemView.findViewById(R.id.title);
            overview = itemView.findViewById(R.id.overview);
            rating = itemView.findViewById(R.id.rating);
            poster = itemView.findViewById(R.id.poster);
        }
    }
}