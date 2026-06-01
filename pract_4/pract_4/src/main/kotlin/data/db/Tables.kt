package data.db

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime

object UsersTable : IntIdTable("users") {
    val username = varchar("username", 100).uniqueIndex()
    val passwordHash = varchar("password_hash", 256)
    val role = varchar("role", 50).default("user")
}

object PrizesTable : IntIdTable("prizes") {
    val awardYear = varchar("award_year", 10)
    val category = varchar("category", 100)
    val fullName = varchar("full_name", 255).default("")
    val motivation = text("motivation").default("")
    val detailLink = varchar("detail_link", 512).default("")
}

object LaureatesTable : IntIdTable("laureates") {
    val prizeId = reference("prize_id", PrizesTable)
    val fullName = varchar("full_name", 255).default("")
    val portion = varchar("portion", 50).default("")
    val motivation = text("motivation").default("")
    val portraitUrl = varchar("portrait_url", 512).default("")
}

object UserPrizesTable : IntIdTable("user_prizes") {
    val userId = reference("user_id", UsersTable)
    val prizeId = reference("prize_id", PrizesTable)
    val addedAt = datetime("added_at")
}
