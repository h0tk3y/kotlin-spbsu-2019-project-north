package databases

import dao.*
import model.Message

class MessageDB : MessageDao {
    override fun addNewMessage(from: UserId, chat: ChatId, text: String, time: Long): MessageId {
        return getNewId().also {
            base[it] = Message(it, from, chat, text, time)
        }
    }

    fun getNewId(): Id = base.size.toLong()

    private val base: MutableMap<MessageId, Message> = hashMapOf()

    override fun getById(elemId: Id): Message? {
        return base[elemId]
    }

    override fun deleteById(elemId: Id) {
        base.remove(elemId)
    }

    override fun findByUser(user: UserId): List<MessageId> = base.entries.mapNotNull {
        if (it.value.from == user) {
            it.key
        } else {
            null
        }
    }

    override fun findSliceFromChat(chat: ChatId, block: Int, last: Int?): List<MessageId> {
        val messages = base.entries.mapNotNull {
            if (it.value.chat == chat) {
                it.key
            } else {
                null
            }
        }
        val pos = last ?: messages.size
        return messages.subList(pos - block, pos)
    }

    override val size
        get() = base.size
}