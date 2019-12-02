package tables

import org.jetbrains.exposed.dao.LongIdTable

object BlockedUsers : LongIdTable() {
    val user = long("user").entityId().references(Users.id)
    val blockedUser = long("blockedUser").entityId().references(Users.id)
}