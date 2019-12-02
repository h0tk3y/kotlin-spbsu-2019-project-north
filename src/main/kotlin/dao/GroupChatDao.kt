package dao

import model.GroupChat

typealias GroupChatId = Long

interface GroupChatDao : ObjectDao<GroupChat> {
    fun addNewGroupChat(owner: UserId, chatName: String, uniqueLink: String?): GroupChat?
    fun searchByName(name: String): List<GroupChat>
    fun getChatByInviteLink(link: String): GroupChat?
}