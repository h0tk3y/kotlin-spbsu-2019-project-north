class ChatDB : ChatDao {
    private val chats = hashMapOf<ChatId, Chat>()

    override fun add(elem: Chat): ChatId {
        val id = chats.size.toLong()
        chats[id] = elem
        return id
    }

    override fun get(elemId: ChatId): Chat? = chats[elemId]

    override fun modify(elemId: ChatId, newElem: Chat) {
        chats[elemId] = newElem
    }

    override fun delete(elemId: ChatId) {
        chats.remove(elemId)
    }

    override val size
        get() = chats.size

    override fun getChatByInviteLink(link: String): ChatId? = chats.entries
        .find { (it.value as? GroupChat)?.uniqueLink == link }?.key

    override fun searchByName(name: String): List<ChatId> = chats.entries.mapNotNull {
        if ((it.value as? GroupChat)?.chatName == name) it.key else null
    }

    override fun searchWithUser(userId: UserId): List<ChatId> = chats.entries.mapNotNull {
        when (val chat = it.value) {
            is PersonalChat -> {
                if (chat.member1 == userId || chat.member2 == userId) it.key
                else null
            }
            is GroupChat -> {
                if (chat.containsUser(userId)) it.key
                else null
            }
            else -> null
        }
    }

}

