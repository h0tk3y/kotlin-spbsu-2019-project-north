package databases

import dao.GroupChatDao
import dao.Id
import dao.UserId
import model.GroupChat
import model.GroupChatToUser
import model.User
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import tables.GroupChats
import tables.GroupChatsToUsers
import tables.getEntityID


class GroupChatDB : GroupChatDao {
    override fun addNewGroupChat(owner: UserId, chatName: String, uniqueLink: String?) =
        transaction {
            val ownerID = getEntityID<User>(owner) ?: return@transaction null
            GroupChat.new {
                this.owner = ownerID
                this.chatName = chatName
                this.uniqueLink = uniqueLink
            }.also {
                GroupChatToUser.new {
                    this.chatId = it.id
                    this.userId = ownerID
                }
            }
        }

    override fun getById(elemId: Id) =
        transaction { GroupChat.findById(elemId) }

    override fun deleteById(elemId: Id) =
        transaction {
            val chat = GroupChat.findById(elemId) ?: return@transaction
            GroupChatsToUsers.deleteWhere { GroupChatsToUsers.chatId eq chat.id }
            chat.delete()
        }

    override val size: Int
        get() = transaction { GroupChat.all().count() }

    override fun searchByName(name: String) =
        transaction { GroupChat.find { GroupChats.chatName eq name }.toList() }

    override fun getChatByInviteLink(link: String) =
        transaction { GroupChat.find { GroupChats.uniqueLink eq link }.singleOrNull() }
}