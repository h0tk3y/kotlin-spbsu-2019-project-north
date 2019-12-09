package databases

import dao.BlockedUsersDao
import dao.UserId
import entries.BlockingOfUserDBEntry
import entries.UserDBEntry
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import tables.BlockedUsers
import tables.getEntityID

class BlockedUsersDB : BlockedUsersDao {
    override fun add(key: UserId, value: UserId): Boolean =
        transaction {
            val keyId = getEntityID<UserDBEntry>(key) ?: return@transaction false
            val valueId = getEntityID<UserDBEntry>(value) ?: return@transaction false
            BlockingOfUserDBEntry.new {
                user = keyId
                blockedUser = valueId
            }
            true
        }

    override fun remove(key: UserId, value: UserId) =
        transaction {
            val keyId = getEntityID<UserDBEntry>(key) ?: return@transaction false
            val valueId = getEntityID<UserDBEntry>(value) ?: return@transaction false
            val expr = (BlockedUsers.user eq keyId) and (BlockedUsers.blockedUser eq valueId)
            BlockingOfUserDBEntry.find { expr }.singleOrNull()?.delete() != null
        }

    override fun select(key: UserId) =
        transaction {
            val keyId = getEntityID<UserDBEntry>(key) ?: return@transaction emptyList<UserId>()
            BlockedUsers.select { BlockedUsers.user eq keyId }.map { it[BlockedUsers.blockedUser].value }
        }

    override fun contains(key: UserId, value: UserId): Boolean =
        transaction {
            val keyId = getEntityID<UserDBEntry>(key) ?: return@transaction false
            val valueId = getEntityID<UserDBEntry>(value) ?: return@transaction false
            !BlockedUsers.select {
                (BlockedUsers.user eq keyId) and (BlockedUsers.blockedUser eq valueId)
            }.empty()
        }
}