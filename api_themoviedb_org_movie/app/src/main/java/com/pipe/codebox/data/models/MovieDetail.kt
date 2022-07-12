package com.pipe.codebox.data.models

import com.google.gson.annotations.SerializedName

data class MovieDetail(
    @SerializedName("adult")
    val adult: Boolean, // false
    @SerializedName("backdrop_path")
    val backdropPath: String, // /dFYguAfeVt19qAbzJ5mArn7DEJw.jpg
    @SerializedName("belongs_to_collection")
    val belongsToCollection: BelongsToCollection,
    @SerializedName("budget")
    val budget: Int, // 94000000
    @SerializedName("genres")
    val genres: List<Genre>,
    @SerializedName("homepage")
    val homepage: String, // http://movies.disney.com/finding-nemo
    @SerializedName("id")
    val id: Int, // 12
    @SerializedName("imdb_id")
    val imdbId: String, // tt0266543
    @SerializedName("original_language")
    val originalLanguage: String, // en
    @SerializedName("original_title")
    val originalTitle: String, // Finding Nemo
    @SerializedName("overview")
    val overview: String, // Nemo, an adventurous young clownfish, is unexpectedly taken from his Great Barrier Reef home to a dentist's office aquarium. It's up to his worrisome father Marlin and a friendly but forgetful fish Dory to bring Nemo home -- meeting vegetarian sharks, surfer dude turtles, hypnotic jellyfish, hungry seagulls, and more along the way.
    @SerializedName("popularity")
    val popularity: Double, // 73.719
    @SerializedName("poster_path")
    val posterPath: String, // /eHuGQ10FUzK1mdOY69wF5pGgEf5.jpg
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany>,
    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountry>,
    @SerializedName("release_date")
    val releaseDate: String, // 2003-05-30
    @SerializedName("revenue")
    val revenue: Int, // 940335536
    @SerializedName("runtime")
    val runtime: Int, // 100
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage>,
    @SerializedName("status")
    val status: String, // Released
    @SerializedName("tagline")
    val tagline: String, // There are 3.7 trillion fish in the ocean. They're looking for one.
    @SerializedName("title")
    val title: String, // Finding Nemo
    @SerializedName("video")
    val video: Boolean, // false
    @SerializedName("vote_average")
    val voteAverage: Double, // 7.8
    @SerializedName("vote_count")
    val voteCount: Int // 14651
)