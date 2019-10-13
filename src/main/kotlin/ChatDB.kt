object ChatDB : ChatDao {
    val chats: MutableMap<ChatId, Chat> = hashMapOf()

    override fun add(elem: Chat): ChatId {
        val id = chats.size.toLong()
        chats.put(id, elem)
        return id
     }

    override fun get(elemId: ChatId): Chat? = chats.get(elemId)

    override fun modify(elemId: ChatId, newElem: Chat) {
        chats.put(elemId, newElem)
    }

    override fun delete(elemId: ChatId) {
        chats.remove(elemId)
    }
    override fun getChatByInviteLink(link: String): ChatId? = chats.entries
        .find {
             when (val chat = it.value) {
                 is GroupChat -> chat.uniqueLink == link
                 else -> false
             }
        }?.key
    override fun searchByName(name: String): List<ChatId> = chats.entries
        .mapNotNull { if ((it.value as? GroupChat)?.chatName?.equals(name) ?: false) it.key else null }

    override fun searchWithUser(userId: UserId): List<ChatId> = chats.entries
         .mapNotNull {
            when (val chat = it.value) {
                is PersonalChat -> {
                    if (chat.member1 == userId || chat.member2 == userId) it.key
                    else null
                }
                is GroupChat -> {
                    if (chat.users.contains(userId)) it.key
                    else null
                }
                else -> null
            }
        }
}

