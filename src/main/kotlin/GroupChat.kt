class GroupChat(val owner: User) : Chat(LinkedList(), LinkedList()) {
    init {
        members.add(owner)
    }

    enum class Type {
        PRIVATE,
        PUBLIC
    }

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