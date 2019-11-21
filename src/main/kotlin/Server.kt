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

    fun register(name: String?, email: String?, phoneNumber: String?, login: String?, password: String?)
            : RegisterUserInfo {
        when (name) {
            null -> RegisterUserInfo(message = "Invalid name")
            else -> when {
                email == null -> RegisterUserInfo(message = "Invalid email")
                !email.contains('@') -> RegisterUserInfo(message = "Incorrect email")
                else -> when {
                    phoneNumber == null -> RegisterUserInfo(message = "Invalid email")
                    phoneNumber.chars().anyMatch(Character::isLetter) ->
                        RegisterUserInfo(message = "Incorrect phone number")
                    else -> when {
                        login == null -> RegisterUserInfo(message = "Invalid login")
                        userBase.existsLogin(login) ->
                            RegisterUserInfo(message = "User with such login already exists")
                        else -> when {
                            password == null ->
                                RegisterUserInfo(message = "Invalid password")
                            password.length < 6 ->
                                RegisterUserInfo(message = "Password is too short")
                            else -> RegisterUserInfo(user = userBase.addNewUser(name, email, phoneNumber, login, password))
                        }
                    }
                }
            }
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
