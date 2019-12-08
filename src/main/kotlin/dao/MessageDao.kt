package dao

import model.Message

typealias MessageId = Long

interface MessageDao : ObjectDao<Message> {
    fun addNewMessage(from: UserId, isPersonal: Boolean, chat: Id, text: String): Message?
    fun findByUser(user: UserId): List<Message>
    fun getMessagesFromChat(isPersonal: Boolean, chat: Id): List<Message>
//    fun findSliceFromChat(isPersonal : Boolean, chat: Id, block: Int, last: Int? = null): List<Message>
}