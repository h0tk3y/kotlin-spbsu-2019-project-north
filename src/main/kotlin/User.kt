data class User(
    val id: UserId,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val chats: MutableList<ChatId>,
    val blockedUsers: MutableList<UserId>
)
