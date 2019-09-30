import java.util.*

class UserBase(
    var allUsers: LinkedList<User>
) {
    fun searchByName(name: String) {
        return allUsers.all{it.name == name}
    }

    fun searchByMail(mail: String)
    {
        return allUsers.all{it.mail == mail}
    }
}
