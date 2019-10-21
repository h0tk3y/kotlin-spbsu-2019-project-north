import org.koin.core.KoinComponent
import org.koin.core.inject

data class GroupChat(
    val messageDB: MessageDao,
    val owner: UserId,
    val chatName: String,
    val uniqueLink: String? = null
) : Chat

