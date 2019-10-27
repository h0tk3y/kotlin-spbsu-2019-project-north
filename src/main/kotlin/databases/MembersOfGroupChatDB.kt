package databases

import dao.ChatId
import dao.MembersOfGroupChatDao
import dao.UserId

class MembersOfGroupChatDB : MembersOfGroupChatDao {
    private val base: MutableSet<Pair<ChatId, UserId>> = mutableSetOf()
    override fun add(key: ChatId, value: UserId): Boolean = base.add(key to value)
    override fun remove(key: ChatId, value: UserId) = base.remove(key to value)
    override fun select(key: ChatId) = base.mapNotNull {
        if (it.first == key) it.second else null
    }
    override fun contains(key: ChatId, value: UserId): Boolean = base.contains(key to value)
}