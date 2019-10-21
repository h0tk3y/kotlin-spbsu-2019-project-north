data class ChatMessages(private val messageBase: MessageDao) : ChatMessageDao {
    private val messageList: MutableList<MessageId> = mutableListOf()

    override fun getById(elemId: MessageId): Message? {
        return if (messageList.contains(elemId)) {
            messageBase.getById(elemId)
        } else {
            null
        }
    }

    override fun addWithNewId(elem: Message): MessageId {
        val id = messageBase.addWithNewId(elem)
        messageList.add(id)
        return id
    }

    override fun modifyById(elemId: MessageId, newElem: Message) = messageBase.modifyById(elemId, newElem)

    override fun deleteById(elemId: MessageId) {
        messageBase.deleteById(elemId)
        messageList.remove(elemId)
    }

    override val size
        get() = messageList.size

    override fun searchByText(text: String): List<MessageId> = messageList.filter {
        messageBase.getById(it)?.text?.contains(text) ?: false
    }

    override fun getBlock(lastPos: Int?, blockSize: Int): List<MessageId> {
        val pos = lastPos ?: messageList.size
        return messageList.subList(pos - blockSize, pos - 1)
    }
}

