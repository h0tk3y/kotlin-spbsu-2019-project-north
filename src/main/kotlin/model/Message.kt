package model

import dao.ChatId
import dao.MessageId
import dao.UserId

data class Message(
    val id: MessageId,
    val from: UserId,
    val chat: ChatId,
    var text: String,
    val time: Long,
    var isDeleted: Boolean = false,
    var isEdited: Boolean = false
)