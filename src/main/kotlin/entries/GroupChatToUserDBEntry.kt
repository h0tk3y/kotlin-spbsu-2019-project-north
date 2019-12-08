package entries

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import tables.GroupChatsToUsers

class GroupChatToUserDBEntry(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<GroupChatToUserDBEntry>(GroupChatsToUsers)

    var chatId by GroupChatsToUsers.chatId
    var userId by GroupChatsToUsers.userId
}