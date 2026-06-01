package com.hophey.pract1.data.repository

import android.util.Log
import com.hophey.pract1.data.dto.toEntity
import com.hophey.pract1.data.network.ApiService
import com.hophey.pract1.domain.entity.Photo
import com.hophey.pract1.domain.repository.PhotoRepository

class PhotoRepositoryImpl(private val api: ApiService) : PhotoRepository {
    override suspend fun getPhotos(): Result<List<Photo>> = runCatching {
        val response = api.getPhotos()
        response
            .map {
                it.toEntity()
            }
    }
}