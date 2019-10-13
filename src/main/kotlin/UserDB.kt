object UserDB : UserDao {
    val users: MutableMap<UserId, User> = mutableMapOf()
    override fun add(elem: User): UserId {
        val id = users.size.toLong()
        users.put(id, elem)
        return id
     }

    override fun get(elemId: UserId): User? = users.get(elemId)

    override fun modify(elemId: UserId, newElem: User) {
        users.put(elemId, newElem)
    }

    override fun delete(elemId: UserId) {
        users.remove(elemId)
    }

    override fun searchByName(name: String): List<UserId> = users.entries
        .mapNotNull { if (it.value.name == name) it.key else null }

    override fun getByEmail(email: String): UserId? = users.entries
        .find { it.value.email == email }?.key

    override fun getByPhoneNumber(phoneNumber: String): UserId? = users.entries
        .find { it.value.phoneNumber == phoneNumber }?.key
}

