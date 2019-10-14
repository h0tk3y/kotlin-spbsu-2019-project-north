data class GroupChat(
    val messageDB: MessageDao,
    val owner: UserId,
    val chatName: String,
    val uniqueLink: String? = null
) : Chat {
    override val messages: ChatMessageDao = ChatMessages(messageDB)
    private val users: MutableSet<UserId> = hashSetOf(owner)

    fun addUser(user: UserId, link: String? = null): Boolean {
        return if (user !in users && link == uniqueLink) {
            users.add(user)
            true
        } else {
            false
        }
    }

    fun containsUser(user: UserId): Boolean = users.contains(user)
    val countUsers
        get() = users.size

    fun leave(user: UserId) {
        users.remove(user)
    }
}

