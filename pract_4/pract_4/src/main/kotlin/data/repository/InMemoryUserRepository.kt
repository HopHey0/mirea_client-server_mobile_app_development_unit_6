package data.repository

import domain.entity.User
import domain.repository.UserRepository
import java.security.MessageDigest

class InMemoryUserRepository : UserRepository {

    private val users: List<User> = listOf(
        User(username = "admin", passwordHash = hash("admin123")),
        User(username = "user", passwordHash = hash("password"))
    )

    override fun findByUsername(username: String): User? =
        users.find { it.username == username }

    companion object {
        fun hash(input: String): String {
            val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
            return bytes.joinToString("") { "%02x".format(it) }
        }
    }
}
