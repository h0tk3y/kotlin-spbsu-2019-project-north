package databases

import dao.UserDao
import dao.UserId
import model.User

class UserDB : UserDao {
    private val users: MutableMap<UserId, User> = mutableMapOf()

    override fun addWithNewId(elem: User): UserId {
        val id = users.size.toLong()
        users[id] = elem
        return id
    }

    override fun getById(elemId: UserId): User? = users[elemId]

    override fun modifyById(elemId: UserId, newElem: User) {
        users[elemId] = newElem
    }

    override fun deleteById(elemId: UserId) {
        users.remove(elemId)
    }

    override val size
        get() = users.size

    override fun searchByName(name: String): List<UserId> = users.entries
        .mapNotNull { if (it.value.name == name) it.key else null }

    override fun getByEmail(email: String): UserId? = users.entries
        .find { it.value.email == email }?.key

    override fun getByPhoneNumber(phoneNumber: String): UserId? = users.entries
        .find { it.value.phoneNumber == phoneNumber }?.key
}

