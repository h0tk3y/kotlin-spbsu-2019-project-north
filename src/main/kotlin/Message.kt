interface Attachment {}
class Message(
    var text: String, val data: Attachment, val from: User, val time: Int,
    val isDeleted: Boolean, val id: Long
) {
    fun edit(user: User, newText: String) {
        text = newText
    }
}