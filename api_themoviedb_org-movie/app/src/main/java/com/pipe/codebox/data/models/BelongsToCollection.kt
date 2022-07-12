package com.pipe.codebox.data.models

import com.google.gson.annotations.SerializedName

data class BelongsToCollection(
    @SerializedName("backdrop_path")
    val backdropPath: String, // /2hC8HHRUvwRljYKIcQDMyMbLlxz.jpg
    @SerializedName("id")
    val id: Int, // 137697
    @SerializedName("name")
    val name: String, // Finding Nemo Collection
    @SerializedName("poster_path")
    val posterPath: String // /xwggrEugjcJDuabIWvK2CpmK91z.jpg
)