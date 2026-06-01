package domain.entity

data class Laureate(
    val id: Int,
    val prizeId: Int,
    val fullName: String,
    val motivation: String,
)

data class NobelPrize(
    val id: Int,
    val awardYear: String,
    val category: String,
    val detailLink: String,
    val laureates: List<Laureate> = emptyList()
)

data class User(
    val id: Int,
    val username: String,
    val passwordHash: String,
    val role: String
)