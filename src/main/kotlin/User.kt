data class User(
    val id: UserId,
    val name: String,
    val email: String,
    val phoneNumber: String,
    var login: String,
    var password: String
) {
    private val chats: MutableList<ChatId> = mutableListOf()
    private val blockedUsers: MutableList<UserId> = mutableListOf()
    private val contacts: MutableList<Contact> = mutableListOf()
}
