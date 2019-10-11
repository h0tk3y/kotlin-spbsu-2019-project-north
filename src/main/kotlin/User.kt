interface User {
    val id: Id
    val name: String
    val email: String
    val phoneNumber: String
    val chats: MutableList<Id>
}