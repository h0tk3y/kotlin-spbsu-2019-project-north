import org.koin.core.KoinComponent
import org.koin.core.inject

data class PersonalChat(
    val member1: UserId,
    val member2: UserId
) : Chat, KoinComponent {
    override val messages: ChatMessageDao by inject()
}
