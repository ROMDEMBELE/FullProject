package data.database.sqlDelight

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.dembeyo.data.MySqlDelightDatabase

actual class DriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(MySqlDelightDatabase.Schema, context, "my-dnd-app.db")
    }
}