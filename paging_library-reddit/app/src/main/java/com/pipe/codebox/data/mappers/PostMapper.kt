package com.pipe.codebox.data.mappers

import com.pipe.codebox.data.models.ServerDataPage
import com.pipe.codebox.domain.entity.PostEntity

internal fun ServerDataPage.toPostEntity() = PostEntity(
    title = this.data.title ?: "",
    totalAwardsReceived = this.data.total_awards_received ?: 0,
    numComments = this.data.num_comments
)

internal fun List<ServerDataPage>.toPostEntity(): List<PostEntity> = this.map { it.toPostEntity() }