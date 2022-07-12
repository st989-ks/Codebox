package com.pipe.codebox.data.models

import com.google.gson.annotations.SerializedName

data class MovieList(
    @SerializedName("page")
    val page: Int, // 1
    @SerializedName("results")
    val results: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int, // 1000
    @SerializedName("total_results")
    val totalResults: Int // 20000
)