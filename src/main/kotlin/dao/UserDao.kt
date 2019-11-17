package dao

import io.ktor.auth.UserPasswordCredential
import model.User
import javax.print.attribute.standard.JobOriginatingUserName

typealias UserId = Long
interface UserDao : ObjectDao<User> {
    fun addNewUser(name: String, email: String, phoneNumber: String, login: String, password: String): UserId
    fun searchByName(name: String): List<UserId>
    fun getByEmail(email: String): UserId?
    fun getByPhoneNumber(phoneNumber: String): UserId?
    fun getUserByCredentials(credential: UserPasswordCredential): User?
}
