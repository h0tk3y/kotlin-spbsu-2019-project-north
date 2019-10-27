typealias ChatId = Long

interface ChatDao : ObjectDao<Chat> {
    fun getChatByInviteLink(link: String) : ChatId?
    fun searchByName(name: String): List<ChatId>
    fun searchWithUser(userId: UserId): List<ChatId>
}