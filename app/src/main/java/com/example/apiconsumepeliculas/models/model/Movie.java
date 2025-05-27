package com.example.apiconsumepeliculas.models.model;

import java.util.List;

public class Movie {
    private int id;
    private String title;
    private String overview;
    private String poster_path;
    private String backdrop_path;
    private String release_date;
    private double vote_average;
    private int vote_count;
    private boolean adult;
    private List<Integer> genre_ids;
    private String original_language;
    private String original_title;
    private double popularity;
    private boolean video;

    // Constructor
    public Movie(int id, String title, String overview, String poster_path,
                 String backdrop_path, String release_date, double vote_average,
                 int vote_count, boolean adult, List<Integer> genre_ids,
                 String original_language, String original_title,
                 double popularity, boolean video) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.adult = adult;
        this.genre_ids = genre_ids;
        this.original_language = original_language;
        this.original_title = original_title;
        this.popularity = popularity;
        this.video = video;
    }

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getOverview() { return overview; }
    public String getPosterPath() { return poster_path; }
    public String getBackdropPath() { return backdrop_path; }
    public String getReleaseDate() { return release_date; }
    public double getVoteAverage() { return vote_average; }
    public int getVoteCount() { return vote_count; }
    public boolean isAdult() { return adult; }
    public List<Integer> getGenreIds() { return genre_ids; }
    public String getOriginalLanguage() { return original_language; }
    public String getOriginalTitle() { return original_title; }
    public double getPopularity() { return popularity; }
    public boolean isVideo() { return video; }
}