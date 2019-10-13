interface ChatMessageDao : MessageDao {
    fun searchByText(text: String): List<MessageId>
    fun getBlock(lastPos: Int?, blockSize: Int): List<MessageId>
}