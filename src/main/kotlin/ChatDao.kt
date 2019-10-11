typealias ChatId = Long
interface ChatDao : Dao<Char> {
    fun getChatByInviteLink(link: String) : Id?
}