package tables

import org.jetbrains.exposed.dao.LongIdTable

object Users : LongIdTable() {
    val name = varchar("name", 50)
    val email = varchar("email", 50).primaryKey()
    val phoneNumber = varchar("Phone number", 20).primaryKey()
    val login = varchar("login", 50).primaryKey()
    val password = varchar("password", 30)
}