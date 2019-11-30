package databases

import dao.BlockedUsersDao
import dao.UserId
import model.User
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import tables.BlockedUsers
import tables.getEntityID

class BlockedUsersDB : BlockedUsersDao {
    override fun add(key: UserId, value: UserId): Boolean =
        transaction {
            val keyId = getEntityID<User>(key) ?: return@transaction false
            val valueId = getEntityID<User>(value) ?: return@transaction false
            BlockedUsers.insert {
                it[user] = keyId
                it[blockedUser] = valueId
            }
            true
        }

    override fun remove(key: UserId, value: UserId) =
        transaction {
            val keyId = getEntityID<User>(key) ?: return@transaction false
            val valueId = getEntityID<User>(value) ?: return@transaction false
            val expr = (BlockedUsers.user eq keyId) and (BlockedUsers.blockedUser eq valueId)
            if (!BlockedUsers.select(expr).empty()) {
                BlockedUsers.deleteWhere {
                    (BlockedUsers.user eq keyId) and (BlockedUsers.blockedUser eq valueId)
                }
                true
            } else {
                false
            }
        }

    override fun select(key: UserId) =
        transaction {
            val keyId = getEntityID<User>(key) ?: return@transaction emptyList<UserId>()
            BlockedUsers.select { BlockedUsers.user eq keyId }.map { it[BlockedUsers.blockedUser].value }
        }

    override fun contains(key: UserId, value: UserId): Boolean =
        transaction {
            val keyId = getEntityID<User>(key) ?: return@transaction false
            val valueId = getEntityID<User>(value) ?: return@transaction false
            !BlockedUsers.select {
                (BlockedUsers.user eq keyId) and (BlockedUsers.blockedUser eq valueId)
            }.empty()
        }
}