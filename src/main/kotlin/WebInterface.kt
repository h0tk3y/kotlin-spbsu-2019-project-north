object CommandHistory {
    public Boolean
    fun containsGuid(Guid guid) {
        // check if a command with the specified guid is already present
    }
}

interface Command {
    private val issuer: User
    private val guid: Guid
    private fun executeCommand()
    public fun dispatch() {
        if (!CommandHistory.containsGuid(guid))
            executeCommand()
    }
}

class UploadDocumentCommand(private val doc: Document) {
    // ну хз
}

open class MessageCommand(private val chat: Chat, private val messageId: Guid) : Command {
}

class SendMessageCommand(private val msg: Message) : MessageCommand {
    override fun executeCommand() {
        issuer.sendMessage(chat, text)
    }
}

class EditMessageCommand(private val msg: Message) : MessageCommand {
    override fun executeCommand() {
        issuer.editMessage(messageId, text)
    }
}

class DeleteMessageCommand : MessageCommand {
    override fun executeCommand() {
        issuer.deleteMessage(messageId)
    }
}

class InviteUserToChatCommand(private val chat: Chat, private val user: User) : Command {
}

class BlockUserCommand(private val user: User) : Command {

}