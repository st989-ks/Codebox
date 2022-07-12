package com.pipe.codebox.domain.entity

data class HomeValue(
    val timeExam: Exam,
    val lessonsHome: List<Lesson>,
    val homeWorks: List<HomeWork>
)
