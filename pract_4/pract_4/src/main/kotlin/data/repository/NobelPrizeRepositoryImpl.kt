package data.repository

import data.db.LaureatesTable
import data.db.PrizesTable
import domain.entity.Laureate
import domain.entity.NobelPrize
import domain.repository.NobelPrizeRepository
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class NobelPrizeRepositoryImpl : NobelPrizeRepository {

    override fun getAllPrizes(): List<NobelPrize> = transaction {
        PrizesTable.selectAll().map { row ->
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

    override fun getPrizeById(id: Int): NobelPrize? = transaction {
        PrizesTable.selectAll().where { PrizesTable.id eq id }.singleOrNull()?.let { row ->
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

    override fun getLaureatesByPrizeId(prizeId: Int): List<Laureate> = transaction {
        LaureatesTable.selectAll().where { LaureatesTable.prizeId eq prizeId }.map { lr ->
            Laureate(
                id = lr[LaureatesTable.id].value,
                prizeId = prizeId,
                fullName = lr[LaureatesTable.fullName],
                motivation = lr[LaureatesTable.motivation],
            )
        }
    }

    override fun getPrizeByYear(year: String): List<NobelPrize> = transaction {
        PrizesTable.selectAll().where { PrizesTable.awardYear eq year }.map { row ->
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
}
