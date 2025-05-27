package com.example.apiconsumepeliculas.View;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.apiconsumepeliculas.Interfaces.MovieDetailsContract;
import com.example.apiconsumepeliculas.R;
import com.example.apiconsumepeliculas.models.model.MovieDetails;
import com.example.apiconsumepeliculas.presenter.MovieDetailsPresenter;
import com.example.apiconsumepeliculas.View.utilsView.SystemUIHelper;

public class MovieDetailsActivity extends AppCompatActivity implements MovieDetailsContract.View {

    private ProgressBar progressBar;
    private MovieDetailsPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        SystemUIHelper.hideSystemUI(this);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.ColorPrimario));

        progressBar = findViewById(R.id.progressBarDetails);

        // Obtener el ID de la película del intent
        int movieId = getIntent().getIntExtra("MOVIE_ID", -1);
        System.out.println("el id es:"+movieId);

        if (movieId == -1) {
            Toast.makeText(this, "Error al cargar los detalles", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        presenter = new MovieDetailsPresenter(this);
        presenter.loadMovieDetails(movieId);
    }

    @Override
    public void showMovieDetails(MovieDetails movieDetails) {
        // Configurar vistas con los detalles de la película
        ImageView backdrop = findViewById(R.id.backdropImage);
        ImageView poster = findViewById(R.id.posterImage);
        TextView title = findViewById(R.id.title);
        TextView originalTitle = findViewById(R.id.originalTitle);
        TextView overview = findViewById(R.id.overview);
        TextView releaseDate = findViewById(R.id.releaseDate);
        TextView runtime = findViewById(R.id.runtime);
        TextView voteAverage = findViewById(R.id.voteAverage);
        TextView voteCount = findViewById(R.id.voteCount);
        TextView genres = findViewById(R.id.genres);
        TextView budget = findViewById(R.id.budget);
        TextView revenue = findViewById(R.id.revenue);
        TextView status = findViewById(R.id.status);
        TextView productionCompanies = findViewById(R.id.productionCompanies);
        Toast.makeText(this,"el id es"+ movieDetails.getId(), Toast.LENGTH_SHORT).show();
        // Cargar imágenes
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/original" + movieDetails.getBackdropPath())
                .into(backdrop);

        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500" + movieDetails.getPosterPath())
                .into(poster);

        // Configurar texto
        title.setText(movieDetails.getTitle());
        originalTitle.setText(movieDetails.getOriginalTitle());
        overview.setText(movieDetails.getOverview());
        releaseDate.setText(movieDetails.getReleaseDate());
        runtime.setText(String.format("%d minutos", movieDetails.getRuntime()));
        voteAverage.setText(String.format("⭐ %.1f", movieDetails.getVoteAverage()));
        voteCount.setText(String.format("(%d votos)", movieDetails.getVoteCount()));

        // Configurar géneros
        StringBuilder genresText = new StringBuilder();
        for (MovieDetails.Genre genre : movieDetails.getGenres()) {
            genresText.append(genre.getName()).append(", ");
        }
        if (genresText.length() > 0) {
            genresText.setLength(genresText.length() - 2); // Eliminar la última coma
        }
        genres.setText(genresText.toString());

        // Configurar otros detalles
        budget.setText(String.format("$%,d", movieDetails.getBudget()));
        revenue.setText(String.format("$%,d", movieDetails.getRevenue()));
        status.setText(movieDetails.getStatus());

        // Configurar compañías de producción
        StringBuilder companiesText = new StringBuilder();
        for (MovieDetails.ProductionCompany company : movieDetails.getProductionCompanies()) {
            companiesText.append(company.getName()).append(", ");
        }
        if (companiesText.length() > 0) {
            companiesText.setLength(companiesText.length() - 2);
        }
        productionCompanies.setText(companiesText.toString());
    }

    @Override
    public void showError(String message) {
        System.out.println("ERRORRRR");
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}