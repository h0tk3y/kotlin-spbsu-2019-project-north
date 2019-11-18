package databases

import dao.GroupChatDao
import dao.Id
import dao.UserId
import model.GroupChat
import org.jetbrains.exposed.sql.transactions.transaction
import tables.GroupChats


class GroupChatDB : GroupChatDao {
    override fun addNewGroupChat(owner: UserId, chatName: String, uniqueLink: String?) =
        transaction {
            GroupChat.new {
                this.owner = owner
                this.chatName = chatName
                this.uniqueLink = uniqueLink
            }
        }

    override fun getById(elemId: Id) =
        transaction { GroupChat.findById(elemId) }

    override fun deleteById(elemId: Id) =
        transaction { GroupChat.findById(elemId)?.delete() ?: Unit }

    override val size: Int
        get() = transaction { GroupChat.all().count() }

    override fun searchByName(name: String) =
        transaction { GroupChat.find { GroupChats.chatName eq name } }.toList()

    override fun getChatByInviteLink(link: String) =
        transaction { GroupChat.find { GroupChats.uniqueLink eq link } }.singleOrNull()
}