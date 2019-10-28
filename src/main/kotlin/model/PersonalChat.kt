package model

import dao.UserId

data class PersonalChat(
    val member1: UserId,
    val member2: UserId
) : Chat