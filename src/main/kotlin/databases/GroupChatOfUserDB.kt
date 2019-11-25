package databases

import dao.GroupChatId
import dao.GroupChatsOfUserDao
import dao.UserId
import model.GroupChat
import model.GroupChatToUser
import model.User
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.emptySized
import org.jetbrains.exposed.sql.transactions.transaction
import tables.GroupChatsToUsers
import tables.getEntityID

class GroupChatOfUserDB : GroupChatsOfUserDao {
    override fun add(key: Long, value: GroupChatId): Boolean =
        transaction {
            val user = getEntityID<User>(key) ?: return@transaction null
            val chat = getEntityID<GroupChat>(value) ?: return@transaction null
            GroupChatToUser.new {
                this.chatId = chat
                this.userId = user
            }
        } == null

    override fun remove(key: UserId, value: GroupChatId) =
        transaction {
            val keyId = getEntityID<User>(key) ?: return@transaction false
            val valueId = getEntityID<GroupChat>(value) ?: return@transaction false
            GroupChatToUser
                .find { (GroupChatsToUsers.chatId eq valueId) and (GroupChatsToUsers.userId eq keyId) }
                .singleOrNull()?.delete() != null
        }

    override fun select(key: UserId): List<GroupChatId> =
        transaction {
            val keyId = getEntityID<User>(key) ?: return@transaction emptySized<GroupChatToUser>()
            GroupChatToUser.find { GroupChatsToUsers.userId eq keyId }
        }.map { it.chatId.value }

    override fun contains(key: UserId, value: GroupChatId): Boolean =
        !transaction {
            val keyId = getEntityID<User>(key) ?: return@transaction false
            val valueId = getEntityID<GroupChat>(value) ?: return@transaction false
            GroupChatToUser
                .find { (GroupChatsToUsers.userId eq keyId) and (GroupChatsToUsers.chatId eq valueId) }
                .empty()
        }
}