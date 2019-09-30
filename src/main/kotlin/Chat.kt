import java.util.*

open class Chat(var members: LinkedList<User>, var messages: LinkedList<Message>) {

    fun send(from: User, message: Message) {
        for (member in members) {
            if (!member.isBanned(from)) {
                member.sendNotification(message, this, from)
            }
        }
        messages.add(message)
    }
}