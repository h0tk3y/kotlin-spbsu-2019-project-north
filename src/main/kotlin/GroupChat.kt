class GroupChat(val owner: User, val chatName: String) : Chat(LinkedList(), LinkedList()) {
    init {
        members.add(owner)
        name = chatName
    }

    enum class Type {
        PRIVATE,
        PUBLIC
    }

    val name: String
    val uniqueLink: String? = null
    var groupType: Type = Type.PRIVATE
    fun addMember(user: User, link: String? = null) {
        if (link == uniqueLink) {
            members.add(user)
            user.addChat(this)
        }
    }

    fun removeMember(user: User) {
        members.remove(user)

    }
}