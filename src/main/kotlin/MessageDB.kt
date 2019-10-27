import dao.*
import model.Message

class MessageDB : MessageDao {
    private val base: MutableMap<MessageId, Message> = hashMapOf()

    override fun addWithNewId(elem: Message): Id {
        val id = base.size.toLong()
        base[id] = elem
        return id
    }

    override fun modifyById(elemId: Id, newElem: Message) {
        base[elemId] = newElem
    }

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
        return messages.subList(pos - block, pos - 1)
    }

    override val size
        get() = base.size
}