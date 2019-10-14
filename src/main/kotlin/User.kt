data class User(
    val id: UserId,
    val name: String,
    val email: String,
    val phoneNumber: String,
    var login: String,
    var password: String
) {
    val chats: MutableList<ChatId> = mutableListOf()
    val blockedUsers: MutableList<UserId> = mutableListOf()
}
