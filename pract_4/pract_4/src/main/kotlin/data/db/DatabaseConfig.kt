package data.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database

object DatabaseConfig {
    val config = HikariConfig().apply {
        jdbcUrl =  "jdbc:postgresql://127.0.0.1:5432/NobelPrizes"
        driverClassName = "org.postgresql.Driver"
        username = "postgres"
        password = "postgres"
        maximumPoolSize = 10
        minimumIdle = 5
        idleTimeout = 300000
        connectionTimeout = 30000
    }

    private val dataSource: HikariDataSource by lazy { HikariDataSource(config) }

    fun connect(): Database = Database.connect(dataSource)
}
