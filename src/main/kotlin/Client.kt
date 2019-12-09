class Client () {
    lateinit var user: User
    var token: String? = null

    fun register(name: String, email: String, phoneNumber: String, login: String, password: String) {

    }

    fun login(login: String, password: String) {}
    fun logout() {}

    //fun sendMessage(id: ChatId, text: String) {}

    fun createPublicChat(name: String) {}
    fun createPrivateChat(name: String) {}
    fun createPersonalChat(user2: User) {}

    fun inviteToChat(user2: User) {}
}