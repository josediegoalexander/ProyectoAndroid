package com.example.gestionpeliculasseries.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SerieResponse {
    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<ApiSerie> results; // Usamos ApiSerie aquí
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("total_results")
    private int totalResults;

    public int getPage() {
        return page;
    }

    public List<ApiSerie> getResults() {
        return results;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    // ... setters si los necesitas ...
}