typealias UserId = Long
interface UserDao : Dao<User> {
    var userLogins: List<String>

    fun searchByName(name: String): List<UserId>
    fun getByEmail(email: String): UserId?
    fun getByPhoneNumber(phoneNumber: String): UserId?
}