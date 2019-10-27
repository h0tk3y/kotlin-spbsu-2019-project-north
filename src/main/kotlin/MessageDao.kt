typealias MessageId = Long

interface MessageDao : ObjectDao<Message> {
    fun findByUser(user: UserId): List<MessageId>
    fun findSliceFromChat(chat: ChatId, block: Int, last: Int?): List<MessageId>
}