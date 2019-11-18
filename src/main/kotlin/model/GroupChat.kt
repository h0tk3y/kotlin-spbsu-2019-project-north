package model

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import tables.GroupChats

class GroupChat(id: EntityID<Long>) : LongEntity(id), Chat {
    companion object : LongEntityClass<GroupChat>(GroupChats)

    var owner by GroupChats.owner
    var chatName by GroupChats.chatName
    var uniqueLink by GroupChats.uniqueLink
}

