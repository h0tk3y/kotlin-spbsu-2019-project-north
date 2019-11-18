package tables

import org.jetbrains.exposed.dao.LongIdTable

object GroupChats : LongIdTable() {
    val owner = long("owner")
    val chatName = varchar("chatName", 50).index()
    val uniqueLink = varchar("uniqueLink", 50).primaryKey().nullable().index()
}