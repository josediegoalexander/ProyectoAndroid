package com.example.gestionpeliculasseries.models;

import com.google.gson.annotations.SerializedName;

public class ApiSerie {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("overview")
    private String overview;
    @SerializedName("first_air_date")
    private String firstAirDate;
    @SerializedName("vote_average")
    private double voteAverage;
    @SerializedName("number_of_seasons")
    private int numberOfSeasons;
    @SerializedName("status")
    private String status;
    // ... otros campos ...

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public String getStatus() {
        return status;
    }

    // ... Getters y Setters para los demás campos ...
}