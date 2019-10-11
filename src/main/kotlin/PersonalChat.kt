class PersonalChat(val member1: UserImpl, val member2: UserImpl) : Chat(mutableListOf(), mutableListOf()) {
    init {
        members = mutableListOf(member1, member2)
        member1.addChat(this)
        member2.addChat(this)
    }
}
