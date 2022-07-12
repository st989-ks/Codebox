package com.pipe.codebox.data.models

data class ClassResponse(
    val title: String,
    val teacher: String,
    val icon: Int,
    val scheduler: String,
    val description: String,
    val bigCircle: Boolean = false,
    val greenBackground:Boolean = false,
    val video: Boolean = false,
    )