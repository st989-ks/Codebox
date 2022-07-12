package com.pipe.codebox.data.mappers

import com.pipe.codebox.data.models.ClassResponse
import com.pipe.codebox.data.models.ExamResponse
import com.pipe.codebox.data.models.HomeWorkResponse
import com.pipe.codebox.domain.entity.*

internal fun ClassResponse.toEntityLessonHome() = Lesson(
    title = this.title,
    icon = this.icon,
    scheduler = this.scheduler,
    video = this.video,
    greenBackground = this.greenBackground
)

@JvmName("toEntityClass")
internal fun List<ClassResponse>.toEntityLessonHome(): List<Lesson> = this.map { it.toEntityLessonHome() }


internal fun ExamResponse.toEntityExam() = Exam(
    date = this.date,
)

internal fun HomeWorkResponse.toEntityHomeWork() = HomeWork(
title=this.title,
icon=this.icon,
deadLine=this.deadLine,
work=this.work,
tagIconOne=this.tagIconOne,
tagIconTwo=this.tagIconTwo
)

@JvmName("toEntityHomeWork")
internal fun List<HomeWorkResponse>.toEntityHomeWork(): List<HomeWork> = this.map { it.toEntityHomeWork() }



internal fun ClassResponse.toEntityDataClass() = DataClass(
title=this.title,
teacher=this.teacher,
icon=this.icon,
scheduler=this.scheduler,
description=this.description,
bigCircle=this.bigCircle,
greenBackground=this.greenBackground,
video=this.video
)

@JvmName("toEntityDataClass")
internal fun List<ClassResponse>.toEntityDataClass(): List<DataClass> = this.map { it.toEntityDataClass() }




