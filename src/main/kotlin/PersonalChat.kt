import Chat
import java.util.*

class PersonalChat(val member1: User, val member2: User) : Chat(MutableList(), MutableList()) {
    init {
        members = MutableListOf(member1, member2)
        member1.addChat(this)
        member2.addChat(this)
    }

}
