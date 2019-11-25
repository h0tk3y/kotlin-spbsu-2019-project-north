package tables

import org.jetbrains.exposed.dao.LongIdTable

object Contacts : LongIdTable() {
    val userId = long("userId").entityId().references(Users.id).primaryKey(0)
    val contactId = long("contactId").entityId().references(Users.id).primaryKey(1)
    val name = varchar("name", 50)
}