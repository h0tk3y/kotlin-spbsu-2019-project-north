import dao.*
import io.ktor.auth.UserPasswordCredential
import model.Message
import model.User
import org.koin.core.KoinComponent
import org.koin.core.inject

class RegisterUserInfo(
    val message: String? = null,
    val user: User? = null
)

class Server : KoinComponent {
    val userBase: UserDao by inject()
    val personalChatBase: PersonalChatDao by inject()
    val groupChatBase: GroupChatDao by inject()
    val messageBase: MessageDao by inject()
    val blockedUsersBase: BlockedUsersDao by inject()
    val groupChatsOfUserBase: GroupChatsOfUserDao by inject()
    val membersOfGroupChatBase: MembersOfGroupChatDao by inject()
    val contactsOfUserBase: ContactsOfUserDao by inject()

//    fun register(name: String?, email: String?, phoneNumber: String?, login: String?, password: String?)
//            : RegisterUserInfo {
//        when (name) {
//            null -> RegisterUserInfo(message = "Invalid name")
//            else -> when {
//                email == null -> RegisterUserInfo(message = "Invalid email")
//                !email.contains('@') -> RegisterUserInfo(message = "Incorrect email")
//                else -> when {
//                    phoneNumber == null -> RegisterUserInfo(message = "Invalid email")
//                    phoneNumber.chars().anyMatch(Character::isLetter) ->
//                        RegisterUserInfo(message = "Incorrect phone number")
//                    else -> when {
//                        login == null -> RegisterUserInfo(message = "Invalid login")
//                        userBase.existsLogin(login) ->
//                            RegisterUserInfo(message = "User with such login already exists")
//                        else -> when {
//                            password == null ->
//                                RegisterUserInfo(message = "Invalid password")
//                            password.length < 6 ->
//                                RegisterUserInfo(message = "Password is too short")
//                            else -> RegisterUserInfo(user = userBase.addNewUser(name, email, phoneNumber, login, password))
//                        }
//                    }
//                }
//            }
//        }
//    }

    fun getUserByCredentials(credentials: UserPasswordCredential): User? = userBase.getUserByCredentials(credentials)

    fun getChats(userId: UserId): List<GroupChatId> = getPersonalChats(userId).plus(getGroupChats(userId))
    fun getPersonalChats(userId: UserId) = personalChatBase.selectWithUser(userId)

    fun getGroupChats(userId: UserId) = groupChatsOfUserBase.select(userId)

    fun getContacts(userId: UserId) = contactsOfUserBase.select(userId)

    fun getChatMessages(chatid: Id, isPersonal: Boolean, block: Int, last: Int?): List<Message> =
        messageBase.findSliceFromChat(isPersonal, chatid, block, last)

    fun sendMessage(from: UserId, isPersonal: Boolean, chatId: Id, text: String) =
        messageBase.addNewMessage(from, isPersonal, chatId, text)

    fun createGroupChat(userId: UserId, name: String, uniqueLink: String?) =
        groupChatBase.addNewGroupChat(userId, name, uniqueLink)

    fun createPersonalChat(user1: UserId, user2: UserId) =
        personalChatBase.addNewPersonalChat(user1, user2)

    fun blockUser(user: UserId, blockedUser: UserId) =
        blockedUsersBase.block(user, blockedUser)

    fun unBlockUser(user: UserId, blockedUser: UserId) =
        blockedUsersBase.unblock(user, blockedUser)
}
