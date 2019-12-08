package databases

import dao.RegistrationForm
import dao.UserDao
import dao.UserId
import io.ktor.auth.UserPasswordCredential
import model.User
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import tables.Users

class UserDB : UserDao {
    override fun addNewUser(form: RegistrationForm): User =
        transaction {
            User.new {
                this.name = form.name
                this.email = form.email
                this.phoneNumber = form.phoneNumber
                this.login = form.login
                this.password = form.password
            }
        }


    override fun getUserByCredentials(credential: UserPasswordCredential): User? =
        transaction {
            User.find {
                (Users.login eq credential.name) and (Users.password eq credential.password)
            }
        }.singleOrNull()


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
        transaction { User.find { Users.email eq email } }.singleOrNull()

    override fun getByPhoneNumber(phoneNumber: String) =
        transaction { User.find { Users.phoneNumber eq phoneNumber } }.singleOrNull()

    override fun updateName(userId: UserId, newName: String) =
        transaction { User.findById(userId)?.name = newName }

    override fun updateEmail(userId: UserId, newEmail: String) =
        transaction { User.findById(userId)?.email == newEmail }

    override fun existsLogin(login: String): Boolean = !transaction { User.find { Users.login eq login }.empty() }
}

