package dao

import model.PersonalChat

typealias PersonalChatId = Long

interface PersonalChatDao : ObjectDao<PersonalChat> {
    fun addNewPersonalChat(member1: UserId, member2: UserId): PersonalChat?
}