package com.pipe.codebox.data.models

import com.google.gson.annotations.SerializedName

data class ProductionCountry(
    @SerializedName("iso_3166_1")
    val iso31661: String, // CA
    @SerializedName("name")
    val name: String // Canada
)