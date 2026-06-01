package com.hophey.pract2.domain.entity


data class NobelPrize(
    val awardYear: String,
    val category: String,
    val laureates: List<Laureate>
)

data class Laureate(
    val id: String,
    val fullName: String,
    val motivation: String
)