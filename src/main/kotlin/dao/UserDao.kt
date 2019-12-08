package dao

import io.ktor.auth.UserPasswordCredential
import model.User

typealias UserId = Long

data class RegistrationForm(val name: String, val email: String, val phoneNumber: String, val login: String, val password: String)

interface UserDao : ObjectDao<User> {
    fun addNewUser(form: RegistrationForm): User
    fun searchByName(name: String): List<User>
    fun getByEmail(email: String): User?
    fun getByPhoneNumber(phoneNumber: String): User?
    fun getUserByCredentials(credential: UserPasswordCredential): User?
    fun updateName(userId: UserId, newName: String)
    fun updateEmail(userId: UserId, newEmail: String): Boolean
    fun existsLogin(login: String): Boolean
}
