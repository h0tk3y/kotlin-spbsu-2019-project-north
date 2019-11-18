package tables

import org.jetbrains.exposed.dao.LongIdTable

object GroupChatsToUsers : LongIdTable() {
    val chatId = long("chatId").entityId().references(GroupChats.id)
    val userId = long("userId").entityId().references(Users.id)
}