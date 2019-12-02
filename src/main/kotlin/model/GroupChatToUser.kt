package model

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import tables.GroupChatsToUsers

class GroupChatToUser(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<GroupChatToUser>(GroupChatsToUsers)

    var chatId by GroupChatsToUsers.chatId
    var userId by GroupChatsToUsers.userId
}