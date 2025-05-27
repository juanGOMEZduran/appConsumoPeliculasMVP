package com.example.apiconsumepeliculas.View;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apiconsumepeliculas.Interfaces.MovieContract;
import com.example.apiconsumepeliculas.R;
import com.example.apiconsumepeliculas.View.adapter.MoviesAdapter;
import com.example.apiconsumepeliculas.View.utilsView.SystemUIHelper;
import com.example.apiconsumepeliculas.models.model.Movie;
import com.example.apiconsumepeliculas.presenter.MoviePresenter;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieContract.View {

    private ImageButton imageButtonDerecha, imageButtonIzquierda, imageButtonBusqueda;
    private RecyclerView recyclerView;
    EditText editTextBuscarBuscarPelicula;
    private TextView textPages;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private ProgressBar progressBar;
    private MoviesAdapter adapter;
    private MoviePresenter presenter;
    private int currentPage = 1;
    private  int totalPages;
    private List<Movie> movieList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SystemUIHelper.hideSystemUI(this);

        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.ColorPrimario));

        ImageCarousel carousel = findViewById(R.id.carousel);
        carousel.registerLifecycle(getLifecycle());

        List<CarouselItem> listaCarrusel = new ArrayList<>();
        listaCarrusel.add(new CarouselItem(R.drawable.imgtrescarrusel));
        listaCarrusel.add(new CarouselItem(R.drawable.imgunocarrusel));
        listaCarrusel.add(new CarouselItem(R.drawable.imgdoscarrusel));
        listaCarrusel.add(new CarouselItem(R.drawable.imgcuatrocarrusel));

        carousel.setData(listaCarrusel);

        // Configurar el RecyclerView para las películas
        recyclerView = findViewById(R.id.recyclerViewPeliculas);
        textPages=findViewById(R.id.textPages);
        progressBar = findViewById(R.id.progressBar);
        imageButtonDerecha=findViewById(R.id.imageButtonDerecha);
        imageButtonIzquierda=findViewById(R.id.imageButtonIzquierda);
        imageButtonBusqueda=findViewById(R.id.imageButtonBusqueda);
        editTextBuscarBuscarPelicula=findViewById(R.id.editTextBuscarBuscarPelicula);

        movieList = new ArrayList<>();
        adapter = new MoviesAdapter(movieList, movie -> {
            // Cuando se hace clic en una película, abrir la actividad de detalles
            Intent intent = new Intent(MainActivity.this, MovieDetailsActivity.class);
            intent.putExtra("MOVIE_ID", movie.getId());
            startActivity(intent);
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // 3. Cargar películas de API
        presenter = new MoviePresenter(this);
        presenter.loadMovies(currentPage);

        String pag=String.valueOf(currentPage);
        textPages.setText(pag);

        if(currentPage==1){
            imageButtonIzquierda.setVisibility(GONE);
        }else {
            imageButtonIzquierda.setVisibility(VISIBLE);
        }
        imageButtonBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = editTextBuscarBuscarPelicula.getText().toString().trim();
                if (!query.isEmpty()) {
                    editTextBuscarBuscarPelicula.setBackgroundResource(R.drawable.edittext_border);
                    currentPage = 1;
                    presenter.searchMovies(query, currentPage);
                    textPages.setText(String.valueOf(currentPage));


                    if(currentPage == 1){
                        imageButtonIzquierda.setVisibility(GONE);
                    } else {
                        imageButtonIzquierda.setVisibility(VISIBLE);
                    }
                } else {
                    editTextBuscarBuscarPelicula.setBackgroundResource(R.drawable.edittext_border_error);
                    currentPage = 1;
                    presenter.loadMovies(currentPage);
                    textPages.setText(String.valueOf(currentPage));
                    imageButtonIzquierda.setVisibility(GONE);
                }
            }
        });

        imageButtonDerecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadMoreItems();
                if(currentPage==1){
                    imageButtonIzquierda.setVisibility(GONE);
                }else {
                    imageButtonIzquierda.setVisibility(VISIBLE);
                }

            }
        });

        imageButtonIzquierda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFewerItems();
                if(currentPage==1){
                    imageButtonIzquierda.setVisibility(GONE);
                }else {
                    imageButtonIzquierda.setVisibility(VISIBLE);
                }
            }
        });





    }

    private void loadMoreItems() {
        if (currentPage < totalPages) {
            isLoading = true;
            currentPage++;
            String query = editTextBuscarBuscarPelicula.getText().toString().trim();

            if (!query.isEmpty()) {
                presenter.searchMovies(query, currentPage);

            } else {
                presenter.loadMovies(currentPage);
            }

            textPages.setText(String.valueOf(currentPage));

            if(currentPage == 1){
                imageButtonIzquierda.setVisibility(GONE);
            } else {
                imageButtonIzquierda.setVisibility(VISIBLE);
            }
        } else {
            isLastPage = true;
        }
    }

    private void loadFewerItems() {
        if(currentPage > 1) {
            isLoading = true;
            currentPage--;
            String query = editTextBuscarBuscarPelicula.getText().toString().trim();

            if (!query.isEmpty()) {
                presenter.searchMovies(query, currentPage);
            } else {
                presenter.loadMovies(currentPage);
            }

            textPages.setText(String.valueOf(currentPage));

            if(currentPage == 1){
                imageButtonIzquierda.setVisibility(GONE);
            } else {
                imageButtonIzquierda.setVisibility(VISIBLE);
            }
        }
    }


    @Override
    public void showMovies(List<Movie> movies) {
        isLoading = false;


            movieList.clear();


        // Guardamos la posición actual antes de añadir nuevos items
        int previousSize = movieList.size();
        movieList.addAll(movies);
        adapter.notifyDataSetChanged();

        // Si es una nueva página (no la primera), hacer scroll al inicio de los nuevos items
        if (currentPage > 1) {
            recyclerView.post(() -> {
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null) {
                    layoutManager.scrollToPositionWithOffset(previousSize, 0);
                }
            });
        }
    }

    @Override
    public void showPages(int pages) {

        System.out.println("numero de paginas"+pages);
        totalPages=pages;
    }


    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}