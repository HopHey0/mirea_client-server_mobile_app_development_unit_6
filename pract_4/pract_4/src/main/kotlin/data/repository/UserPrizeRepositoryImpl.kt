package data.repository

import data.db.LaureatesTable
import data.db.PrizesTable
import data.db.UserPrizesTable
import domain.entity.Laureate
import domain.entity.NobelPrize
import domain.repository.UserPrizeRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

class UserPrizeRepositoryImpl : UserPrizeRepository {

    override fun getFavorites(userId: Int): List<NobelPrize> = transaction {
        val prizeIds = UserPrizesTable
            .selectAll().where { UserPrizesTable.userId eq userId }
            .map { it[UserPrizesTable.prizeId].value }

        if (prizeIds.isEmpty()) return@transaction emptyList()

        PrizesTable.selectAll().where { PrizesTable.id inList prizeIds }.map { row ->
            val prizeId = row[PrizesTable.id].value
            val laureates = LaureatesTable
                .selectAll().where { LaureatesTable.prizeId eq prizeId }
                .map { lr ->
                    Laureate(
                        id = lr[LaureatesTable.id].value,
                        prizeId = prizeId,
                        fullName = lr[LaureatesTable.fullName],
                        motivation = lr[LaureatesTable.motivation],
                    )
                }
            NobelPrize(
                id = prizeId,
                awardYear = row[PrizesTable.awardYear],
                category = row[PrizesTable.category],
                detailLink = row[PrizesTable.detailLink],
                laureates = laureates
            )
        }
    }

    override fun addFavorite(userId: Int, prizeId: Int): Boolean = transaction {
        val alreadyExists = UserPrizesTable
            .selectAll()
            .where { (UserPrizesTable.userId eq userId) and (UserPrizesTable.prizeId eq prizeId) }
            .count() > 0

        if (alreadyExists) return@transaction false

        UserPrizesTable.insert {
            it[UserPrizesTable.userId] = userId
            it[UserPrizesTable.prizeId] = prizeId
            it[addedAt] = LocalDateTime.now()
        }
        true
    }

    override fun removeFavorite(userId: Int, prizeId: Int): Boolean = transaction {
        val deleted = UserPrizesTable.deleteWhere {
            (UserPrizesTable.userId eq userId) and (UserPrizesTable.prizeId eq prizeId)
        }
        deleted > 0
    }

    override fun isFavorite(userId: Int, prizeId: Int): Boolean = transaction {
        UserPrizesTable
            .selectAll()
            .where { (UserPrizesTable.userId eq userId) and (UserPrizesTable.prizeId eq prizeId) }
            .count() > 0
    }
}
