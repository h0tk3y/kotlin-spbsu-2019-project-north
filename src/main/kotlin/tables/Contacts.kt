package tables

import org.jetbrains.exposed.dao.LongIdTable

object Contacts : LongIdTable() {
    val userId = long("userId").entityId().references(Users.id).primaryKey()
    val contactId = long("contactId").entityId().references(Users.id).primaryKey()
    val name = varchar("name", 50)
}