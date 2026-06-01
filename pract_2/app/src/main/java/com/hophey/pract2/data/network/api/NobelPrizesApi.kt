package com.hophey.pract2.data.network.api

import com.hophey.pract2.data.dto.NobelPrizesResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class NobelPrizesApi(
    private val client: HttpClient
) {
    suspend fun getNobelPrizesByParams(
        year: String? = null,
        limit: Int = 25,
        offset: Int = 0
    ): NobelPrizesResponse {
        return client
            .get("nobelPrizes") {
                parameter("limit", limit)
                parameter("offset", offset)
                year?.let { parameter("nobelPrizeYear", it) }
            }
            .body()
    }
}