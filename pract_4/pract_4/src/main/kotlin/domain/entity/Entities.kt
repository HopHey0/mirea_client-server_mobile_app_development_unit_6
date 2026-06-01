package domain.entity

data class Laureate(
    val id: String,
    val fullName: String,
    val motivation: String
)

data class NobelPrize(
    val awardYear: String,
    val category: String,
    val laureates: List<Laureate> = emptyList()
)

data class User(
    val username: String,
    val passwordHash: String
)
