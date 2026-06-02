package data.repository

import data.db.UsersTable
import domain.entity.User
import domain.repository.UserRepository
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.security.MessageDigest

class UserRepositoryImpl : UserRepository {

    override fun findByUsername(username: String): User? = transaction {
        UsersTable.selectAll().where { UsersTable.username eq username }.singleOrNull()?.let { row ->
            User(
                id = row[UsersTable.id].value,
                username = row[UsersTable.username],
                passwordHash = row[UsersTable.passwordHash],
                role = row[UsersTable.role]
            )
        }
    }

    override fun findById(id: Int): User? = transaction {
        UsersTable.selectAll().where { UsersTable.id eq id }.singleOrNull()?.let { row ->
            User(
                id = row[UsersTable.id].value,
                username = row[UsersTable.username],
                passwordHash = row[UsersTable.passwordHash],
                role = row[UsersTable.role]
            )
        }
    }

    companion object {
        fun hash(input: String): String {
            val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
            return bytes.joinToString("") { "%02x".format(it) }
        }
    }
}
