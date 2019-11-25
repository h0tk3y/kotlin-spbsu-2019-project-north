package databases

import dao.GroupChatId
import dao.MembersOfGroupChatDao
import dao.UserId
import model.GroupChat
import model.GroupChatToUser
import model.User
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.emptySized
import org.jetbrains.exposed.sql.transactions.transaction
import tables.GroupChatsToUsers
import tables.getEntityID

class MembersOfGroupChatDB : MembersOfGroupChatDao {
    override fun add(key: GroupChatId, value: UserId): Boolean =
        transaction {
            val user = getEntityID<User>(value) ?: return@transaction null
            val chat = getEntityID<GroupChat>(key) ?: return@transaction null
            GroupChatToUser.new {
                this.chatId = chat
                this.userId = user
            }
        } == null

    override fun remove(key: GroupChatId, value: UserId) =
        transaction {
            val keyId = getEntityID<GroupChat>(key) ?: return@transaction false
            val valueId = getEntityID<User>(value) ?: return@transaction false
            GroupChatToUser
                .find {
                    (GroupChatsToUsers.chatId eq keyId) and (GroupChatsToUsers.userId eq valueId)
                }.singleOrNull()?.delete() != null
        }

    override fun select(key: GroupChatId) =
        transaction {
            val keyId = getEntityID<GroupChat>(key) ?: return@transaction emptySized<GroupChatToUser>()
            GroupChatToUser.find { GroupChatsToUsers.chatId eq keyId }
        }.map { it.chatId.value }

    override fun contains(key: GroupChatId, value: UserId): Boolean =
        !transaction {
            val keyId = getEntityID<GroupChat>(key) ?: return@transaction false
            val valueId = getEntityID<User>(value) ?: return@transaction false
            GroupChatToUser
                .find { (GroupChatsToUsers.chatId eq keyId) and (GroupChatsToUsers.userId eq valueId) }
                .empty()
        }
}