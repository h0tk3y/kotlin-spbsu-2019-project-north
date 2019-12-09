package databases

import dao.GroupChatId
import dao.MembersOfGroupChatDao
import dao.UserId
import entries.GroupChatDBEntry
import entries.GroupChatToUserDBEntry
import entries.UserDBEntry
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import tables.GroupChatsToUsers
import tables.getEntityID

class MembersOfGroupChatDB : MembersOfGroupChatDao {
    override fun add(key: GroupChatId, value: UserId): Boolean =
        transaction {
            val user = getEntityID<UserDBEntry>(value) ?: return@transaction false
            val chat = getEntityID<GroupChatDBEntry>(key) ?: return@transaction false
            GroupChatToUserDBEntry.new {
                this.chatId = chat
                this.userId = user
            }
            true
        }

    override fun remove(key: GroupChatId, value: UserId) =
        transaction {
            val keyId = getEntityID<GroupChatDBEntry>(key) ?: return@transaction false
            val valueId = getEntityID<UserDBEntry>(value) ?: return@transaction false

            if (valueId.value == GroupChatDBEntry.findById(keyId.value)?.owner?.value) {
                return@transaction false
            }

            GroupChatToUserDBEntry
                .find {
                    (GroupChatsToUsers.chatId eq keyId) and (GroupChatsToUsers.userId eq valueId)
                }.singleOrNull()?.delete() != null
        }

    override fun select(key: GroupChatId) =
        transaction {
            val keyId = getEntityID<GroupChatDBEntry>(key) ?: return@transaction emptyList<UserId>()
            GroupChatToUserDBEntry.find { GroupChatsToUsers.chatId eq keyId }.map { it.userId.value }
        }

    override fun contains(key: GroupChatId, value: UserId): Boolean =
        transaction {
            val keyId = getEntityID<GroupChatDBEntry>(key) ?: return@transaction false
            val valueId = getEntityID<UserDBEntry>(value) ?: return@transaction false
            !GroupChatToUserDBEntry
                .find { (GroupChatsToUsers.chatId eq keyId) and (GroupChatsToUsers.userId eq valueId) }
                .empty()
        }
}