import java.util.*

class UserBase(
    var allUsers: LinkedList<User>
) {
    fun addUser(user: User) {
        allUsers.add(user)
    }

    fun removeUser(user: User) {
        allUsers.remove(user)
    }

    fun searchByName(name: String): List<User> {
        return (allUsers.partition{it.name == name}).first
    }

    fun searchByMail(email: String) : List<User>
    {
        return allUsers.partition{it.email == email}.first
    }
}
