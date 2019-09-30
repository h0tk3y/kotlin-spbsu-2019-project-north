import java.util.*

open class Chat(var members: MutableList<User>, var messages: MutableList<Message>) {

    fun send(from: User, message: Message) {
        for (member in members) {
            if (!member.isBanned(from)) {
                member.sendNotification(message, this, from)
            }
        }
        messages.add(message)
    }
}