typealias MessageId = Long

interface MessageDao : Dao<Message> {
    fun searchByText(text: String): List<MessageId>
}