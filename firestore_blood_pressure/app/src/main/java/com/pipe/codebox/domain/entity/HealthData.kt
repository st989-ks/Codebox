package com.pipe.codebox.domain.entity

data class HealthData(
    val id: Long = 0,
    val date: String = "",
    val time: String = "",
    val upperBloodPressure: String = "",
    val lowerBloodPressure: String = "",
    val pulse: String = ""
)