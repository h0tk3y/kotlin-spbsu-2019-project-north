package model

import MessageDao
import UserId

data class GroupChat(
    val messageDB: MessageDao,
    val owner: UserId,
    val chatName: String,
    val uniqueLink: String? = null
) : Chat

