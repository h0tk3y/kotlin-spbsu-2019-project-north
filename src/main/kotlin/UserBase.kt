import java.util.*

class UserBase(
    var allUsers: LinkedList<UserImpl>
) {
    fun addUser(user: UserImpl) {
        allUsers.add(user)
    }

    fun removeUser(user: UserImpl) {
        allUsers.remove(user)
    }

    fun searchByName(name: String): List<UserImpl> {
        return (allUsers.partition{it.name == name}).first
    }

    fun searchByMail(email: String) : List<UserImpl>
    {
        return allUsers.partition{it.email == email}.first
    }
}
