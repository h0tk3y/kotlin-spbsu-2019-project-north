import dao.BlockedUsersDao
import dao.UserId

class BlockedUsersDB : BlockedUsersDao {
    private val base: MutableSet<Pair<UserId, UserId>> = mutableSetOf()
    override fun add(key: UserId, value: UserId): Boolean = base.add(key to value)
    override fun remove(key: UserId, value: UserId) = base.remove(key to value)
    override fun select(key: UserId) = base.mapNotNull {
        if (it.first == key) it.second else null
    }

    override fun contains(key: UserId, value: UserId): Boolean = base.contains(key to value)
}