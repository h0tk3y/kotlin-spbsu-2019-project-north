package tables

import org.jetbrains.exposed.dao.LongIdTable

object GroupChatsToUsers : LongIdTable() {
    val chatId = entityId("id", GroupChats)
    val userId = entityId("id", Users)
}