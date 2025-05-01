package com.example.gestionpeliculasseries.models;

import com.google.gson.annotations.SerializedName;

public class ApiMovie {
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("overview")
    private String overview;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("vote_average")
    private double voteAverage;
    // ... otros campos ...

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    // ... Getters y Setters para los demás campos ...
}