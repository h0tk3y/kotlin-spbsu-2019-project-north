import dao.*
import io.ktor.application.call
import io.ktor.auth.UserPasswordCredential
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import model.GroupChat
import model.Message
import model.PersonalChat
import model.User
import org.koin.core.KoinComponent
import org.koin.core.inject

class RegisterUserInfo (
    val message : String? = null,
    val user : User? = null
)

class Server : KoinComponent {
    val userBase: UserDao by inject()
    val personalChatBase: PersonalChatDao by inject()
    val groupChatBase: GroupChatDao by inject()
    val messageBase: MessageDao by inject()
    val blockedUsersBase: BlockedUsersDao by inject()
    val chatsOfUserBase: ChatsOfUserDao by inject()
    val membersOfGroupChatBase: MembersOfGroupChatDao by inject()
    val contactsOfUserBase: ContactsOfUserDao by inject()

    fun authenticate(userId: UserId) {
        //return token
    }

    fun register(form: RegistrationForm)
            : RegisterUserInfo =
        when {
            !form.email.contains('@') ->
                RegisterUserInfo(message = "Incorrect email")
            form.phoneNumber.find { !it.isDigit() } != null ->
                RegisterUserInfo(message = "Incorrect phone number")
            userBase.existsLogin(form.login) ->
                RegisterUserInfo(message = "User with such login already exists")
            form.password.length < 6 ->
                RegisterUserInfo(message = "Password is too short")
            else -> RegisterUserInfo(user = userBase.addNewUser(form))
        }
    }

    fun getUserByCredentials(credentials: UserPasswordCredential): User? = userBase.getUserByCredentials(credentials)

    fun getChats(userId: UserId): List<ChatId> = chatsOfUserBase.select(userId)
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
