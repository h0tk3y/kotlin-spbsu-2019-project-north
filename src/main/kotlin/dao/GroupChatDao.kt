package dao

import entries.GroupChatDBEntry

typealias GroupChatId = Long

interface GroupChatDao : ObjectDao<GroupChatDBEntry> {
    fun addNewGroupChat(owner: UserId, chatName: String, uniqueLink: String?): GroupChatDBEntry?
    fun searchByName(name: String): List<GroupChatDBEntry>
    fun getChatByInviteLink(link: String): GroupChatDBEntry?
}