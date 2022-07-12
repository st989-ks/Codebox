package com.pipe.codebox.data.models

import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("id")
    val id: Int, // 10759
    @SerializedName("name")
    val name: String // Action & Adventure
)