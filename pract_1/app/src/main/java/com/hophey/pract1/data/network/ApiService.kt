package com.hophey.pract1.data.network

import com.hophey.pract1.data.dto.PhotoDto
import retrofit2.http.GET

interface ApiService {
    @GET("/v2/list")
    suspend fun getPhotos(): List<PhotoDto>
}