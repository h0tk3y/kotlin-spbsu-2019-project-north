package entries

import User
import io.ktor.auth.Principal
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import tables.Users


class UserDBEntry(id: EntityID<Long>) : LongEntity(id), Principal {
    companion object : LongEntityClass<UserDBEntry>(Users)

    var name by Users.name
    var email by Users.email
    var phoneNumber by Users.phoneNumber
    var login by Users.login
    var password by Users.password

    fun toUser() = User(id.value, name, email, phoneNumber, login, password)
}