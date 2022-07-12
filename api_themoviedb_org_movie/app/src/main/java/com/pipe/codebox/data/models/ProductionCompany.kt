package com.pipe.codebox.data.models

import com.google.gson.annotations.SerializedName

data class ProductionCompany(
    @SerializedName("id")
    val id: Int, // 3
    @SerializedName("logo_path")
    val logoPath: String, // /1TjvGVDMYsj6JBxOAkUHpPEwLf7.png
    @SerializedName("name")
    val name: String, // Pixar
    @SerializedName("origin_country")
    val originCountry: String // US
)