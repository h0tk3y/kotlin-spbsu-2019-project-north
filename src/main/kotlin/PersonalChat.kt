import Chat
class PersonalChat(val partner: User) : Chat(LinkedList(), LinkedList()) {
    init {
        members.add(partner)
    }

}
