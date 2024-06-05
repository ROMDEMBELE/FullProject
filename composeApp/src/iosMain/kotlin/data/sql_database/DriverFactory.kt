package data.sql_database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import org.dembeyo.data.MySqlDelightDatabase

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(MySqlDelightDatabase.Schema, "character.db")
    }
}