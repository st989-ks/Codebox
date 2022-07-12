package com.pipe.codebox.data.models

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("adult")
    val adult: String, // false
    @SerializedName("backdrop_path")
    val backdropPath: String, // /srYya1ZlI97Au4jUYAktDe3avyA.jpg
    @SerializedName("genre_ids")
    val genreIds: List<String>,
    @SerializedName("id")
    val id: String, // 464052
    @SerializedName("original_language")
    val originalLanguage: String, // en
    @SerializedName("original_title")
    val originalTitle: String, // Wonder Woman 1984
    @SerializedName("overview")
    val overview: String, // Wonder Woman comes into conflict with the Soviet Union during the Cold War in the 1980s and finds a formidable foe by the name of the Cheetah.
    @SerializedName("popularity")
    val popularity: String, // 2247.44
    @SerializedName("poster_path")
    val posterPath: String, // /8UlWHLMpgZm9bx6QYh0NFoq67TZ.jpg
    @SerializedName("release_date")
    val releaseDate: String, // 2020-12-16
    @SerializedName("title")
    val title: String, // Wonder Woman 1984
    @SerializedName("video")
    val video: String, // false
    @SerializedName("vote_average")
    val voteAverage: String, // 7
    @SerializedName("vote_count")
    val voteCount: String // 3619
)