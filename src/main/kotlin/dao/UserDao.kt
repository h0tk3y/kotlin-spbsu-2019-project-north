package dao

import entries.UserDBEntry
import io.ktor.auth.UserPasswordCredential

typealias UserId = Long

interface UserDao : ObjectDao<UserDBEntry> {
    fun addNewUser(name: String, email: String, phoneNumber: String, login: String, password: String): UserDBEntry
    fun searchByName(name: String): List<UserDBEntry>
    fun getByEmail(email: String): UserDBEntry?
    fun getByPhoneNumber(phoneNumber: String): UserDBEntry?
    fun getUserByCredentials(credential: UserPasswordCredential): UserDBEntry?
    fun updateName(userId: UserId, newName: String)
    fun updateEmail(userId: UserId, newEmail: String): Boolean
    fun existsLogin(login: String): Boolean
}
