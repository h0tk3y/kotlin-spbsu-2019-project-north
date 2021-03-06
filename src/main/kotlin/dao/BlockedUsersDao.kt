package dao

interface BlockedUsersDao : RelationsDao<UserId, UserId> {
    fun isBlocked(user: UserId, potentiallyBlockedUser: UserId): Boolean =
        contains(user, potentiallyBlockedUser)

    fun block(user: UserId, blockedUser: UserId) = add(user, blockedUser)
    fun unblock(user: UserId, blockedUser: UserId) = remove(user, blockedUser)
}