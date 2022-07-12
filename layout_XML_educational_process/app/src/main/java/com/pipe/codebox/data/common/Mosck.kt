package com.pipe.codebox.data.common

import com.pipe.codebox.R
import com.pipe.codebox.data.models.ClassResponse
import com.pipe.codebox.data.models.ExamResponse
import com.pipe.codebox.data.models.HomeWorkResponse

val listClassResponse = listOf<ClassResponse>(
    ClassResponse(
        title = "History",
        teacher = "Mrs Thomas",
        icon = R.drawable.ic_bow,
        scheduler = "8:00 - 8:45",
        description = "",
        bigCircle = true,
        greenBackground = false,
        video = true,
    ),
    ClassResponse(
        title = "Literature",
        teacher = "Mrs Barros",
        icon = R.drawable.ic_literature,
        scheduler = "9:00 - 9:45",
        description = "",
        bigCircle = false,
        greenBackground = false,
        video = false,
    ),
    ClassResponse(
        title = "Physical Education",
        teacher = "Mrs Barros",
        icon = R.drawable.ic_basketball,
        scheduler = "10:00 - 11:35",
        description = "Intensive preparation for The Junior World Championship in Los Angeles",
        bigCircle = false,
        greenBackground = true,
        video = false,
    ),
    ClassResponse(
        title = "Physics",
        teacher = "Mr Rudy",
        icon = R.drawable.ic_physics,
        scheduler = "12:00 - 12:45",
        description = "",
        bigCircle = false,
        greenBackground = false,
        video = false,
    ),
    ClassResponse(
        title = "Literature",
        teacher = "Mrs Barros",
        icon = R.drawable.ic_literature,
        scheduler = "13:00 - 13:45",
        description = "",
        bigCircle = false,
        greenBackground = false,
        video = false,
    ),
    ClassResponse(
        title = "Physics",
        teacher = "Mr Rudy",
        icon = R.drawable.ic_physics,
        scheduler = "14:00 - 14:45",
        description = "",
        bigCircle = false,
        greenBackground = false,
        video = true,
    ),
)


val examResponse = ExamResponse("022350")

val listHomeWorkResponse = listOf<HomeWorkResponse>(
    HomeWorkResponse(
        title = "Literature",
        icon = R.drawable.ic_literature,
        deadLine = 2,
        work = "Read scenes 1.1–1.12 of The Master and Margarita.",
        tagIconOne = R.drawable.user_1,
        tagIconTwo = R.drawable.user_2
    ),
    HomeWorkResponse(
        title = "Physics",
        icon = R.drawable.ic_physics,
        deadLine = 5,
        work = "Learn newton s 3 laws of motion.",
        tagIconOne = R.drawable.user_4,
        tagIconTwo = R.drawable.user_6
    ),
    HomeWorkResponse(
        title = "History",
        icon = R.drawable.ic_bow,
        deadLine = 7,
        work = "Prepare a report on the foundation of St. Petersburg.",
        tagIconOne = R.drawable.user_8,
        tagIconTwo = R.drawable.user_7
    ),
    HomeWorkResponse(
        title = "Literature",
        icon = R.drawable.ic_literature,
        deadLine = 4,
        work = "Read scenes 1.1–1.12 of The Master and Margarita.",
        tagIconOne = R.drawable.user_5,
        tagIconTwo = R.drawable.user_3
    ),
    HomeWorkResponse(
        title = "Literature",
        icon = R.drawable.ic_literature,
        deadLine = 1,
        work = "Read scenes 1.1–1.12 of The Master and Margarita.",
        tagIconOne = R.drawable.user_2,
        tagIconTwo = R.drawable.user_5
    ),
    HomeWorkResponse(
        title = "Literature",
        icon = R.drawable.ic_literature,
        deadLine = 3,
        work = "Read scenes 1.1–1.12 of The Master and Margarita.",
        tagIconOne = R.drawable.user_8,
        tagIconTwo = R.drawable.user_2
    ),
)