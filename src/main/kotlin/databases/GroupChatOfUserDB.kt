package databases

import dao.GroupChatId
import dao.GroupChatsOfUserDao
import dao.UserId
import entries.GroupChatDBEntry
import entries.GroupChatToUserDBEntry
import entries.UserDBEntry
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import tables.GroupChatsToUsers
import tables.getEntityID

class GroupChatOfUserDB : GroupChatsOfUserDao {
    override fun add(key: UserId, value: GroupChatId): Boolean =
        transaction {
            val user = getEntityID<UserDBEntry>(key) ?: return@transaction false
            val chat = getEntityID<GroupChatDBEntry>(value) ?: return@transaction false
            GroupChatToUserDBEntry.new {
                this.chatId = chat
                this.userId = user
            }
            true
        }

    override fun remove(key: UserId, value: GroupChatId) =
        transaction {
            val keyId = getEntityID<UserDBEntry>(key) ?: return@transaction false
            val valueId = getEntityID<GroupChatDBEntry>(value) ?: return@transaction false

            if (keyId.value == GroupChatDBEntry.findById(valueId.value)?.owner?.value) {
                return@transaction false
            }

            GroupChatToUserDBEntry
                .find { (GroupChatsToUsers.chatId eq valueId) and (GroupChatsToUsers.userId eq keyId) }
                .singleOrNull()?.delete() != null
        }

    override fun select(key: UserId): List<GroupChatId> =
        transaction {
            val keyId = getEntityID<UserDBEntry>(key) ?: return@transaction emptyList<GroupChatId>()
            GroupChatToUserDBEntry.find { GroupChatsToUsers.userId eq keyId }.map { it.chatId.value }
        }

    override fun contains(key: UserId, value: GroupChatId): Boolean =
        transaction {
            val keyId = getEntityID<UserDBEntry>(key) ?: return@transaction false
            val valueId = getEntityID<GroupChatDBEntry>(value) ?: return@transaction false
            !GroupChatToUserDBEntry
                .find { (GroupChatsToUsers.userId eq keyId) and (GroupChatsToUsers.chatId eq valueId) }
                .empty()
        }
}