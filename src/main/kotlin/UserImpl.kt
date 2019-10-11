import java.util.*
import kotlin.collections.HashSet

interface UserAttachments {}
class UserImpl(
    val name: String, val email: String, val id: Long
) {
    var personalChats: MutableList<PersonalChat>
    var groupChats: MutableList<GroupChat>
    var banned: MutableSet<UserImpl>
    lateinit var documents: UserAttachments
    var contacts: MutableList<Contact>

    init {
        groupChats = LinkedList()
        personalChats = LinkedList()
        contacts = LinkedList()
        banned = mutableSetOf()
    }

    fun sendMessage(m: Message, c: Chat) {
        c.send(this, m)
    }

    fun isBanned(other: UserImpl): Boolean {
        return other in banned
    }

    fun ban(other: UserImpl) {
        banned.add(other)
    }

    fun sendNotification(m: Message, c: Chat, from: UserImpl) {
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
