package com.hophey.pract1.domain.repository

import com.hophey.pract1.domain.entity.Photo

interface PhotoRepository {
    suspend fun getPhotos(): Result<List<Photo>>
}