package com.pipe.codebox.data.models

import com.google.gson.annotations.SerializedName

data class Crew(
    @SerializedName("adult")
    val adult: Boolean, // false
    @SerializedName("credit_id")
    val creditId: String, // 57ac7eca92514157f100248d
    @SerializedName("department")
    val department: String, // Production
    @SerializedName("gender")
    val gender: Int, // 2
    @SerializedName("id")
    val id: Int, // 37631
    @SerializedName("job")
    val job: String, // Executive Producer
    @SerializedName("known_for_department")
    val knownForDepartment: String, // Writing
    @SerializedName("name")
    val name: String, // Michael Hirst
    @SerializedName("original_name")
    val originalName: String, // Michael Hirst
    @SerializedName("popularity")
    val popularity: Double, // 1.8
    @SerializedName("profile_path")
    val profilePath: Any // null
)