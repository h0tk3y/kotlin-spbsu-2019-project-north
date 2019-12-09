package dao

import entries.PersonalChatDBEnrty

typealias PersonalChatId = Long

interface PersonalChatDao : ObjectDao<PersonalChatDBEnrty> {
    fun addNewPersonalChat(member1: UserId, member2: UserId): PersonalChatDBEnrty?
    fun selectWithUser(user: UserId): List<PersonalChatId>
}