package databases

import dao.Id
import dao.MessageDao
import dao.UserId
import entries.GroupChatDBEntry
import entries.MessageDBEntry
import entries.PersonalChatDBEnrty
import entries.UserDBEntry
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import tables.Messages

class MessageDB : MessageDao {
    override fun addNewMessage(from: UserId, isPersonal: Boolean, chat: Id, text: String) =
        transaction {
            val fromId = UserDBEntry.findById(from)?.id ?: return@transaction null
            val chatId: Id

            if (isPersonal)
                chatId = PersonalChatDBEnrty.findById(chat)?.id?.value ?: return@transaction null
            else
                chatId = GroupChatDBEntry.findById(chat)?.id?.value ?: return@transaction null

            MessageDBEntry.new {
                this.from = fromId
                this.isPersonal = isPersonal
                this.chat = chatId
                this.text = text
                this.dateTime = DateTime.now()
                this.isDeleted = false
                this.isEdited = false
            }
        }

    override fun getById(elemId: Id) = transaction { MessageDBEntry.findById(elemId) }

    override fun deleteById(elemId: Id) =
        transaction { MessageDBEntry.findById(elemId)?.delete() ?: Unit }

    override fun findByUser(user: UserId) =
        transaction { MessageDBEntry.find { Messages.from eq user }.toList() }

//    override fun findSliceFromChat(isPersonal: Boolean, chat: Id, block: Int, last: Int?) =
//        transaction {
//            val chatMessages =
//                MessageDBEntry
//                    .find { (Messages.isPersonal eq isPersonal) and (Messages.chat eq chat) }
//                    .orderBy(Messages.dateTime to SortOrder.ASC)
//            val offset = last ?: (maxOf(chatMessages.count() - block, 0))
//            chatMessages.limit(block, offset).toList().reversed()
//        }

    override fun getMessagesFromChat(isPersonal: Boolean, chat: Id): List<MessageDBEntry> =
        transaction {
            MessageDBEntry
                .find { (Messages.isPersonal eq isPersonal) and (Messages.chat eq chat) }
                .orderBy(Messages.dateTime to SortOrder.ASC).toList()
        }

    override val size
        get() = transaction { MessageDBEntry.all().count() }
}