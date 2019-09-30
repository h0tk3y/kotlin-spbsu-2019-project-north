import Chat
import java.util.*

class PersonalChat(val partner: User) : Chat(LinkedList(), LinkedList()) {
    init {
        members.add(partner)
    }

}
