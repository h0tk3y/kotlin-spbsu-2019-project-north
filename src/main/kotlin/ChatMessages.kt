data class ChatMessages(private val messageBase: MessageDao) : ChatMessageDao {
    private val messageList: MutableList<MessageId> = mutableListOf()

    override fun get(elemId: MessageId): Message? {
        return if (messageList.contains(elemId)) {
            messageBase.get(elemId)
        } else {
            null
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

    override val size
        get() = messageList.size

    override fun searchByText(text: String): List<MessageId> = messageList.filter {
        messageBase.get(it)?.text?.contains(text) ?: false
    }

    override fun getBlock(lastPos: Int?, blockSize: Int): List<MessageId> {
        val pos = lastPos ?: messageList.size
        return messageList.subList(pos - blockSize, pos - 1)
    }
}

