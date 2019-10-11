data class PersonalChat(
    val messageDB: MessageDao,
    val member1: UserId,
    val member2: UserId
) : Chat {
    override val messages: ChatMessageDao = ChatMessages(messageDB)
}
