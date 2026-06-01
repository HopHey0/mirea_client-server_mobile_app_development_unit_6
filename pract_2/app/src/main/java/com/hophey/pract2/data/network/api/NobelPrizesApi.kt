package com.hophey.pract2.data.network.api

import com.hophey.pract2.data.dto.NobelPrizesResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class NobelPrizesApi(
    private val client: HttpClient
) {
    suspend fun getNobelPrizesByParams(query: String): NobelPrizesResponse{
        return client
            .get { parameter("q", query) }
            .body()
    }
}