typealias ChatId = Long

interface ChatDao : Dao<Chat> {
    fun getChatByInviteLink(link: String) : ChatId?
    fun searchByName(name: String): List<ChatId>
    fun searchWithUser(userId: UserId): List<ChatId>
}