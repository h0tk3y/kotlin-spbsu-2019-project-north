package entries

import Message
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import tables.Messages

class MessageDBEntry(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<MessageDBEntry>(Messages)

    var from by Messages.from
    var isPersonal by Messages.isPersonal
    var chat by Messages.chat
    var text by Messages.text
    var dateTime by Messages.dateTime
    var isDeleted by Messages.isDeleted
    var isEdited by Messages.isEdited

    fun toMessage() =
        Message(id.value, from.value, isPersonal, chat, text, dateTime, isDeleted, isEdited)
}