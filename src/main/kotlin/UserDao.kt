interface UserDao : Dao<User> {
    fun searchByName(name: String): List<Id>
    fun getByEmail(email: String): Id?
    fun getByPhoneNumber(phoneNumber: String): Id?
}