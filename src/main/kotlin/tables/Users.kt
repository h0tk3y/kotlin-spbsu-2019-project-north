package tables

import org.jetbrains.exposed.dao.LongIdTable

object Users : LongIdTable() {
    val name = varchar("name", 50)
    val email = varchar("email", 50).uniqueIndex()
    val phoneNumber = varchar("Phone number", 20).uniqueIndex()
    val login = varchar("login", 50).uniqueIndex()
    val password = varchar("password", 30)
}