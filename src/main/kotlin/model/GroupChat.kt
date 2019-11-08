package model

import dao.UserId

data class GroupChat(
    val owner: UserId,
    val chatName: String,
    val uniqueLink: String? = null
) : Chat

