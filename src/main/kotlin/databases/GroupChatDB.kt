package databases

import dao.GroupChatDao
import dao.Id
import dao.UserId
import entries.GroupChatDBEntry
import entries.GroupChatToUserDBEntry
import entries.UserDBEntry
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import tables.GroupChats
import tables.GroupChatsToUsers
import tables.getEntityID


class GroupChatDB : GroupChatDao {
    override fun addNewGroupChat(owner: UserId, chatName: String, uniqueLink: String?) =
        transaction {
            val ownerID = getEntityID<UserDBEntry>(owner) ?: return@transaction null
            GroupChatDBEntry.new {
                this.owner = ownerID
                this.chatName = chatName
                this.uniqueLink = uniqueLink
            }.also {
                GroupChatToUserDBEntry.new {
                    this.chatId = it.id
                    this.userId = ownerID
                }
            }
        }

    override fun getById(elemId: Id) =
        transaction { GroupChatDBEntry.findById(elemId) }

    override fun deleteById(elemId: Id) =
        transaction {
            val chat = GroupChatDBEntry.findById(elemId) ?: return@transaction
            if (!GroupChatsToUsers.select { GroupChatsToUsers.chatId eq chat.id }.empty()) {
                GroupChatsToUsers.deleteWhere { GroupChatsToUsers.chatId eq chat.id }
            }
            chat.delete()
        }

    override val size: Int
        get() = transaction { GroupChatDBEntry.all().count() }

    override fun searchByName(name: String) =
        transaction { GroupChatDBEntry.find { GroupChats.chatName eq name }.toList() }

    override fun getChatByInviteLink(link: String) =
        transaction { GroupChatDBEntry.find { GroupChats.uniqueLink eq link }.singleOrNull() }
}