import org.koin.core.KoinComponent
import org.koin.core.inject

data class GroupChat(
    val owner: UserId,
    val chatName: String,
    val uniqueLink: String? = null
) : Chat, KoinComponent {

    override val messages: ChatMessageDao by inject()

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

