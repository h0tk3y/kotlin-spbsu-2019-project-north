package databases

import dao.Id
import dao.MessageDao
import dao.UserId
import model.Message
import model.User
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import tables.Messages

class MessageDB : MessageDao {
    override fun addNewMessage(from: UserId, typeOfChat: Boolean, chat: Id, text: String) =
        transaction {
            val fromId = User.findById(from)?.id ?: return@transaction null
            Message.new {
                this.from = fromId
                this.typeOfChat = typeOfChat
                this.chat = chat
                this.text = text
                this.dateTime = DateTime.now()
                this.isDeleted = false
                this.isEdited = false
            }
        }

    override fun getById(elemId: Id) = transaction { Message.findById(elemId) }

    override fun deleteById(elemId: Id) =
        transaction { Message.findById(elemId)?.delete() ?: Unit }

    override fun findByUser(user: UserId) =
        transaction { Message.find { Messages.from eq user }.toList() }

    override fun findSliceFromChat(type: Boolean, chat: Id, block: Int, last: Int?) =
        transaction {
            val chatMessages =
                Message
                    .find { (Messages.typeOfChat eq type) and (Messages.chat eq chat) }
                    .orderBy(Messages.dateTime to SortOrder.ASC)
            val offset = last ?: (maxOf(chatMessages.count() - block, 0))
            chatMessages.limit(block, offset).toList().reversed()
        }


    override val size
        get() = transaction { Message.all().count() }
}