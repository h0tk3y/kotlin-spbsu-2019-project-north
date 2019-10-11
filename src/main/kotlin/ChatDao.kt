interface ChatDao : Dao<Char> {
    fun getChatByInviteLink(link: String) : Id?
}