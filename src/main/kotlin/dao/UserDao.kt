package dao

import io.ktor.auth.UserPasswordCredential
import model.User

typealias UserId = Long

interface UserDao : ObjectDao<User> {
    fun addNewUser(name: String, email: String, phoneNumber: String, login: String, password: String): User
    fun searchByName(name: String): List<User>
    fun getByEmail(email: String): User?
    fun getByPhoneNumber(phoneNumber: String): User?
    fun getUserByCredentials(credential: UserPasswordCredential): User?
    fun updateName(userId: UserId, newName: String)
    fun updateEmail(userId: UserId, newEmail: String) : Boolean
}
