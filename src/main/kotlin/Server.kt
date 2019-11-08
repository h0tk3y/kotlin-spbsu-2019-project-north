import dao.*
import model.*
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
    fun getChats(userId: UserId): List<Chat> {
        return emptyList()
    }

    fun getPersonalChats(userId: UserId): List<PersonalChat> {
        return emptyList()
    }

    fun getGroupChats(userId: UserId): List<GroupChat> {
        return emptyList()
    }

    fun getContacts(userId: UserId): List<Contact> {
        return emptyList()
    }

    fun getChatMessages(id: ChatId): List<Message> {
        return emptyList()
    }

    fun sendMessage(id: ChatId) {}

    fun createGroupChat(id: UserId, name: String): ChatId {
        return 0
    }

    fun createPersonalChat(id: UserId, name: String): ChatId {
        return 0
    }


}
