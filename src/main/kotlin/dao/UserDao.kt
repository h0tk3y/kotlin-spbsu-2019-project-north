package dao

import io.ktor.auth.UserPasswordCredential
import model.User

typealias UserId = Long
interface UserDao : ObjectDao<User> {
    fun searchByName(name: String): List<UserId>
    fun getByEmail(email: String): UserId?
    fun getByPhoneNumber(phoneNumber: String): UserId?
    fun getUserByCredentials(credential: UserPasswordCredential): User?
}