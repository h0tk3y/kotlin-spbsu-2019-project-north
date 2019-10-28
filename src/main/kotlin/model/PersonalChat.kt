package model

import dao.UserId
import model.Chat

data class PersonalChat(
    val member1: UserId,
    val member2: UserId
) : Chat