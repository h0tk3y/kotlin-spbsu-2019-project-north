import Chat
import java.util.*

class PersonalChat(val member1: User, val member2: User) : Chat(mutableListOf(), mutableListOf()) {
    init {
        members = mutableListOf(member1, member2)
        member1.addChat(this)
        member2.addChat(this)
    }

}
