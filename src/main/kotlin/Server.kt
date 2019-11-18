import dao.*
import io.ktor.auth.UserPasswordCredential
import model.GroupChat
import model.Message
import model.PersonalChat
import model.User
import org.koin.core.KoinComponent
import org.koin.core.inject

class Server : KoinComponent {
    val userBase: UserDao by inject()
    val personalChatBase: PersonalChatDao by inject()
    val groupChatBase: GroupChatDao by inject()
    val messageBase: MessageDao by inject()
    val blockedUsersBase: BlockedUsersDao by inject()
    val groupChatsOfUserBase: GroupChatsOfUserDao by inject()
    val membersOfGroupChatBase: MembersOfGroupChatDao by inject()
    val contactsOfUserBase: ContactsOfUserDao by inject()

    fun authenticate(userId: UserId) {
        //return token
    }

    fun register(user: User) {

    }

    fun getUserByCredentials(credentials: UserPasswordCredential): User? = userBase.getUserByCredentials(credentials)

    fun getChats(userId: UserId): List<ChatId> = groupChatsOfUserBase.select(userId)
    fun getPersonalChats(userId: UserId) = getChats(userId).mapNotNull { chatBase.getById(it) as? PersonalChat }

    fun getGroupChats(userId: UserId) = getChats(userId).mapNotNull { chatBase.getById(it) as? GroupChat }

    fun getContacts(userId: UserId) = contactsOfUserBase.select(userId)

    fun getChatMessages(id: Id): List<Message> {
        return emptyList()
    }

    fun sendMessage(chatId: Id, from: UserId, text: String) {
        TODO()
    }

    fun createGroupChat(userId: UserId, name: String): Id {
        TODO()
    }

    fun createPersonalChat(user1: UserId, user2: UserId): Id {
        TODO()
    }


}
