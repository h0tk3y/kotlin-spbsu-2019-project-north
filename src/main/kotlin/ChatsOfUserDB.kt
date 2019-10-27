class ChatsOfUserDB : ChatsOfUserDao {
    private val base: MutableSet<Pair<UserId, ChatId>> = mutableSetOf()
    override fun add(key: UserId, value: ChatId): Boolean = base.add(key to value)
    override fun remove(key: UserId, value: ChatId) = base.remove(key to value)
    override fun select(key: UserId) = base.mapNotNull {
        if (it.first == key) it.second else null
    }
    override fun contains(key: UserId, value: ChatId): Boolean = base.contains(key to value)
}