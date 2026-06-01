package com.hophey.pract1.data.dto

import com.hophey.pract1.domain.entity.Photo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoDto(
    val id: String,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String,
    @SerialName("download_url")
    val downloadUrl: String
)

@Serializable
data class PhotosResponse(
    val photos: List<PhotoDto>
)

fun PhotoDto.toEntity() = Photo(
    id = this.id,
    author = this.author,
    width = this.width,
    height = this.height,
    url = this.url,
    downloadUrl = this.downloadUrl
)