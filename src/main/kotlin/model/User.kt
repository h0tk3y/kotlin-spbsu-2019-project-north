package model

import dao.UserId
import io.ktor.auth.Principal

data class User(
    val id: UserId,
    val name: String,
    val email: String,
    val phoneNumber: String,
    var login: String,
    var password: String
) : Principal
