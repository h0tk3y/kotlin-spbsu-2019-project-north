object ChatBase : ChatDao {
    val base: MutableMap<ChatId, Chat> = hashMapOf()

    override fun add(elem: Chat): Id {
        val id = base.size.toLong()
        base.put(id, elem)
        return id
     }

    override fun get(elemId: ChatId): Chat? = base.get(elemId)

    override fun modify(elemId: ChatId, newElem: Chat) {
        base.put(elemId, newElem)
    }

    override fun delete(elemId: ChatId) {
        base.remove(elemId)
    }
    override fun getChatByInviteLink(link: String): ChatId? = base.entries
        .find {
             val chat = it.value as? GroupChat
             if (chat == null) {
                 false
             } else {
                 chat.uniqueLink == link
             }
        }?.key
    override fun searchByName(name: String): List<ChatId> = base.entries
        .filter {
            val chat = it.value as? GroupChat
            if (chat == null) {
                false
            } else {
                chat.chatName == name
            }
         }.map { it.key }.toList()
    override fun searchWithUser(userId: UserId): List<ChatId> = base.entries
         .filter {
             val chat = it.value
             if (chat is PersonalChat) {
                  chat.member1 == userId ||
                  chat.member2 == userId
             } else {
                 (chat as GroupChat).users.contains(userId)
             }
          }.map { it.key }.toList()
}

