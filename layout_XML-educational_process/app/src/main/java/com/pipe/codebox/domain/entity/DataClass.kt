package com.pipe.codebox.domain.entity

data class DataClass(
    val title: String,
    val teacher: String,
    val icon: Int,
    val scheduler: String,
    val description: String,
    val bigCircle: Boolean = false,
    val greenBackground: Boolean = false,
    val video: Boolean = false,
)