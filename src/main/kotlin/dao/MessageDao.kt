package dao

import entries.MessageDBEntry

typealias MessageId = Long

interface MessageDao : ObjectDao<MessageDBEntry> {
    fun addNewMessage(from: UserId, isPersonal: Boolean, chat: Id, text: String): MessageDBEntry?
    fun findByUser(user: UserId): List<MessageDBEntry>
    fun findSliceFromChat(type: Boolean, chat: Id, block: Int, last: Int? = null): List<MessageDBEntry>
}