import java.util.*
import kotlin.collections.HashSet

interface UserAttachments {}
class User(
    var personalChats: LinkedList<PersonalChat>,
    var groupChats: LinkedList<GroupChat>,
    var banned: HashSet<User>,
    var documents: UserAttachments
) {


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

    fun searchChat(chat: Chat, query: Regex) = chat.messages.filter {
        query.containsMatchIn(it.text) && !isBanned(it.from)
    }.map { Pair(chat, it) }


    fun search(query: String) = personalChats.map { searchChat(it, Regex(query)) }
        .plus(personalChats.map { searchChat(it, Regex(query)) })

}
