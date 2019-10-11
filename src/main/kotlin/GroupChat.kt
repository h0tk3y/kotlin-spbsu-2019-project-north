data class GroupChat(
    val messageDB: MessageDao,
    val owner: UserId,
    val chatName: String,
    val uniqueLink: String? = null
) : Chat {
    override val messages: ChatMessageDao = ChatMessages(messageDB)
    val users: MutableList<UserId> = mutableListOf(owner)

    fun addUser(user: UserId, link: String? = null) {
        if (link == uniqueLink) {
            users.add(user)
        }
    }

    fun leave(user: UserId) {
        users.remove(user)
    }
}

