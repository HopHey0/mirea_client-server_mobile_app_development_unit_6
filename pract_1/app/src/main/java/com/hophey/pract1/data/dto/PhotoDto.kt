package com.hophey.pract1.data.dto

import com.hophey.pract1.domain.entity.Photo
import kotlinx.serialization.SerialName

data class PhotoDto(
    val id: Int,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String,
    @SerialName("download_url")
    val downloadUrl: String
)


fun PhotoDto.toEntity() = Photo(
    id = this.id,
    author = this.author,
    width = this.width,
    height = this.height,
    url = this.url,
    downloadUrl = this.downloadUrl
)