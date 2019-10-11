data class ChatMessages(val messageBase: MessageDao) : ChatMessageDao {
    val messageList: MutableList<MessageId> = mutableListOf()
    override fun get(elemId: MessageId): Message? {
        if (messageList.contains(elemId)) {
            return messageBase.get(elemId)
        } else {
            return null
        }
    }

    override fun add(elem: Message): MessageId {
        val id = messageBase.add(elem)
        messageList.add(id)
        return id
    }

    override fun modify(elemId: MessageId, newElem: Message) = messageBase.modify(elemId, newElem)

    override fun delete(elemId: MessageId) {
        messageBase.delete(elemId)
        messageList.remove(elemId)
    }
    override fun searchByText(text: String): List<MessageId> {
        return messageList.filter {
            val s = messageBase.get(it)
            if (s == null) {
                false
            } else {
                s.text == text
            }
         }
    }
    override fun getBlock(last: MessageId, blockSize: Int): List<MessageId> {
        val pos = messageList.indexOf(last)
        return messageList.subList(pos - blockSize, pos - 1)
    }
}

