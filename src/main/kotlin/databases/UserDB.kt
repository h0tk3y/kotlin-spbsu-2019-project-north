package databases

import dao.UserDao
import dao.UserId
import io.ktor.auth.UserPasswordCredential
import model.User
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import tables.Users

class UserDB : UserDao {
    override fun addNewUser(name: String, email: String, phoneNumber: String, login: String, password: String): User =
        transaction {
            User.new {
                this.name = name
                this.email = email
                this.phoneNumber = phoneNumber
                this.login = login
                this.password = password
            }
        }


    override fun getUserByCredentials(credential: UserPasswordCredential): User? =
        transaction {
            User.find {
                (Users.login eq credential.name) and (Users.password eq credential.password)
            }
        }.firstOrNull()


    override fun getById(elemId: UserId): User? =
        transaction {
            User.findById(elemId)
        }

    override fun deleteById(elemId: UserId) =
        transaction {
            User.findById(elemId)?.delete() ?: Unit
        }

    override val size
        get() = transaction { User.all().count() }

    override fun searchByName(name: String): List<User> =
        transaction { User.find { Users.name eq name } }.toList()

    override fun getByEmail(email: String) =
        transaction { User.find { Users.email eq email } }.firstOrNull()

    override fun getByPhoneNumber(phoneNumber: String) =
        transaction { User.find { Users.phoneNumber eq phoneNumber } }.firstOrNull()

    override fun updateName(userId: UserId, newName: String) =
        transaction { User.findById(userId)?.name = newName }

    override fun updateEmail(userId: UserId, newEmail: String) =
        transaction { User.findById(userId)?.email == newEmail }
}

