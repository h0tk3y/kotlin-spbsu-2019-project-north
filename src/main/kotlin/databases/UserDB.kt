package databases

import dao.UserDao
import dao.UserId
import entries.UserDBEntry
import io.ktor.auth.UserPasswordCredential
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import tables.Users

class UserDB : UserDao {
    override fun addNewUser(
        name: String,
        email: String,
        phoneNumber: String,
        login: String,
        password: String
    ): UserDBEntry =
        transaction {
            UserDBEntry.new {
                this.name = name
                this.email = email
                this.phoneNumber = phoneNumber
                this.login = login
                this.password = password
            }
        }


    override fun getUserByCredentials(credential: UserPasswordCredential): UserDBEntry? =
        transaction {
            UserDBEntry.find {
                (Users.login eq credential.name) and (Users.password eq credential.password)
            }.singleOrNull()
        }


    override fun getById(elemId: UserId): UserDBEntry? =
        transaction {
            UserDBEntry.findById(elemId)
        }

    override fun deleteById(elemId: UserId) =
        transaction {
            UserDBEntry.findById(elemId)?.delete() ?: Unit
        }

    override val size
        get() = transaction { UserDBEntry.all().count() }

    override fun searchByName(name: String): List<UserDBEntry> =
        transaction { UserDBEntry.find { Users.name eq name }.toList() }

    override fun getByEmail(email: String) =
        transaction { UserDBEntry.find { Users.email eq email }.singleOrNull() }

    override fun getByPhoneNumber(phoneNumber: String) =
        transaction { UserDBEntry.find { Users.phoneNumber eq phoneNumber }.singleOrNull() }

    override fun updateName(userId: UserId, newName: String) =
        transaction { UserDBEntry.findById(userId)?.name = newName }

    override fun updateEmail(userId: UserId, newEmail: String) =
        transaction {
            val user = UserDBEntry.findById(userId)
            if (user != null && getByEmail(newEmail) == null) {
                user.email = newEmail
                true
            } else {
                false
            }
        }

    override fun existsLogin(login: String): Boolean =
        !transaction { UserDBEntry.find { Users.login eq login }.empty() }
}

