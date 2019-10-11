interface ChatMessageDao : MessageDao {
    fun searchByText(text: String): List<MessageId>
    fun getBlock(last: MessageId, blockSize: Int): List<MessageId>
}