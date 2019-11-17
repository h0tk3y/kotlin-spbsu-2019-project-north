package model

import io.ktor.auth.Principal
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import tables.Users


class User(id: EntityID<Long>) : LongEntity(id), Principal {
    companion object : LongEntityClass<User>(Users)

    var name by Users.name
    var email by Users.email
    var phoneNumber by Users.phoneNumber
    var login by Users.login
    var password by Users.password
}
