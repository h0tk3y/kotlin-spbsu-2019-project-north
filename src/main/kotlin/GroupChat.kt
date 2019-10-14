data class GroupChat(
    val messageDB: MessageDao,
    val owner: UserId,
    val chatName: String,
    val uniqueLink: String? = null
) : Chat {
    override val messages: ChatMessageDao = ChatMessages(messageDB)
    val users: MutableSet<UserId> = hashSetOf(owner)

    fun addUser(user: UserId, link: String? = null): Boolean {
        if (user !in users && link == uniqueLink) {
            users.add(user)
            return true
        } else {
            return false
        }
    }

    fun leave(user: UserId) {
        users.remove(user)
    }
}

