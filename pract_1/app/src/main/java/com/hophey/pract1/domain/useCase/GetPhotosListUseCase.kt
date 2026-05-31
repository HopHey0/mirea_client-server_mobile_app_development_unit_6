package com.hophey.pract1.domain.useCase

import com.hophey.pract1.domain.entity.Photo
import com.hophey.pract1.domain.repository.PhotoRepository

class GetPhotosListUseCase(
    private val photoRepository: PhotoRepository
) {
    suspend operator fun invoke(): Result<List<Photo>> = photoRepository.getPhotos()
}