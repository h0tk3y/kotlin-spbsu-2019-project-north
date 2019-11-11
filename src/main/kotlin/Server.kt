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
    val chatBase: ChatDao by inject()
    val messageBase: MessageDao by inject()
    val blockedUsersBase: BlockedUsersDao by inject()
    val chatsOfUserBase: ChatsOfUserDao by inject()
    val membersOfGroupChatBase: MembersOfGroupChatDao by inject()
    val contactsOfUserBase: ContactsOfUserDao by inject()

    fun authenticate(userId: UserId) {
        //return token
    }

    fun register(userId: UserId) {}
    fun getUserByCredentials(credentials: UserPasswordCredential): User? = userBase.getUserByCredentials(credentials)

    fun getChats(userId: UserId): List<ChatId> = chatsOfUserBase.select(userId)
    fun getPersonalChats(userId: UserId) = getChats(userId).mapNotNull { chatBase.getById(it) as? PersonalChat }

    fun getGroupChats(userId: UserId) = getChats(userId).mapNotNull { chatBase.getById(it) as? GroupChat }

    fun getContacts(userId: UserId) = contactsOfUserBase.select(userId)

    fun getChatMessages(id: ChatId): List<Message> {
        return emptyList()
    }

    fun sendMessage(chatId: ChatId, from: UserId, text: String) {
        val time = System.currentTimeMillis()
        val id = messageBase.addNewMessage(from, chatId, text, time)
    }

    fun createGroupChat(userId: UserId, name: String): ChatId {
        val id = chatBase.addWithNewId(GroupChat(userId, name))
        chatsOfUserBase.add(userId, id)
        return id
    }

    fun createPersonalChat(user1: UserId, user2: UserId): ChatId {
        val id = chatBase.addWithNewId(PersonalChat(user1, user2))
        chatsOfUserBase.add(user1, id)
        chatsOfUserBase.add(user2, id)
        return id
    }


}
