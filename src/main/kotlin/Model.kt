import dao.GroupChatId
import dao.MessageId
import dao.PersonalChatId
import dao.UserId
import io.ktor.auth.Principal
import java.util.*

data class User(
    val id: UserId,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val login: String,
    val password: String
) : Principal

data class Message(
    val id: MessageId,
    val from: UserId,
    val isPersonal: Boolean,
    val chat: Long,
    val text: String,
    val datetime: Date,
    val isDeleted: Boolean,
    val isEdited: Boolean
)

data class PersonalChat(
    val id: PersonalChatId,
    val member1: UserId,
    val member2: UserId
)

data class GroupChat(
    val id: GroupChatId,
    val owner: UserId,
    val chatName: String,
    val uniqueLink: String?
)

data class Contact(
    val id: Long,
    val userId: UserId,
    val contactId: UserId,
    val name: String
)