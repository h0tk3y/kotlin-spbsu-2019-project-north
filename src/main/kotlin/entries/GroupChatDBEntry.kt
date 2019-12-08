package entries

import GroupChat
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import tables.GroupChats

class GroupChatDBEntry(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<GroupChatDBEntry>(GroupChats)

    var owner by GroupChats.owner
    var chatName by GroupChats.chatName
    var uniqueLink by GroupChats.uniqueLink

    fun toGroupChat() = GroupChat(id.value, owner.value, chatName, uniqueLink)
}

