class Message(
    val messageId: MessageId,
    val from: UserId,
    val chat: ChatId,
    var text: String,
    val time: Int,
    var isDeleted: Boolean = false,
    var isEdited: Boolean = false
)