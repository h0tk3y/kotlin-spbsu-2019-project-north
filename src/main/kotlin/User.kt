import java.util.*
import kotlin.collections.HashSet

interface UserAttachments {}
class User(
    val name: String, val email: String, val id: Long
) {
    var personalChats: MutableList<PersonalChat>
    var groupChats: MutableList<GroupChat>
    var banned: MutableSet<User>
    lateinit var documents: UserAttachments
    var contacts: MutableList<Contact>

    init {
        groupChats = LinkedList()
        personalChats = LinkedList()
        contacts = LinkedList()
        banned = HashSet()
    }

    fun sendMessage(m: Message, c: Chat) {
        c.send(this, m)
    }

    fun isBanned(other: User): Boolean {
        return other in banned
    }

    fun ban(other: User) {
        banned.add(other)
    }

    fun sendNotification(m: Message, c: Chat, from: User) {
        TODO("Send notification")
    }

    fun addChat(chat: Chat) {
        if (chat is PersonalChat) {
            personalChats.add(chat)
        } else if (chat is GroupChat) {
            groupChats.add(chat)
        }
    }

}
