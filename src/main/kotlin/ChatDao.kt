//11.10.2019. Аля

typealias ChatId = Long

interface ChatDao : Dao<Char> {
    fun getChatByInviteLink(link: String) : Id?
    fun searchByName(name: String): List<ChatId>
    fun searchByUserName(userName: String): List<ChatId>
    fun searchByUser(userId: UserId): List<ChatId>
}