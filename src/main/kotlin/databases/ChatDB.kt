package databases

import dao.ChatDao
import dao.ChatId
import model.Chat
import model.GroupChat

class ChatDB : ChatDao {

    private val chats = hashMapOf<ChatId, Chat>()

    override fun getById(elemId: ChatId): Chat? = chats[elemId]

    override fun deleteById(elemId: ChatId) {
        chats.remove(elemId)
    }

    override val size
        get() = chats.size

    override fun getChatByInviteLink(link: String): ChatId? = chats.entries
        .find { (it.value as? GroupChat)?.uniqueLink == link }?.key

    override fun searchByName(name: String): List<ChatId> = chats.entries.mapNotNull {
        if ((it.value as? GroupChat)?.chatName == name) it.key else null
    }
}

