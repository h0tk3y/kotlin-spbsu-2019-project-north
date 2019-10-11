class Client (var user: User){
    fun registration(login: String, password: String) {

    }

    fun logIn() {}
    fun logOut() {}

    fun sendMessage(id: ChatId, text: String) {}

    fun createPublicChat(name: String) {}
    fun createPrivateChat(name: String) {}
    fun createPersonalChat(user2: User) {}

    fun inviteToChat(user2: User) {}
}