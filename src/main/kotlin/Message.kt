import com.sun.org.apache.xpath.internal.operations.Bool

//11.10.2019. Аля

class Message(var text: String,
              val from: UserId,
              val to: ChatId,
              val time: Int,
              var isDeleted: Boolean = false,
              var isEdited: Boolean = false)
{
    fun edit(newText: String) {
        text = newText
        isEdited = true
    }

    fun delete() {
        text = "This message has been deleted"
        isDeleted = true
    }

}