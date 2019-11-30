package model

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import tables.BlockedUsers

class BlockingOfUser(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<BlockingOfUser>(BlockedUsers)

    var user by BlockedUsers.user
    var blockedUser by BlockedUsers.blockedUser
}