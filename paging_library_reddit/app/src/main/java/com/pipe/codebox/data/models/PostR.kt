package com.pipe.codebox.data.models

data class PostR(
    val id: String,
    val title: String?,
    val author: String?,
    val num_comments: String,
    val name: String,
    val total_awards_received: Int?
)
