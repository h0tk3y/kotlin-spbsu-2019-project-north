interface BlockedUsersDao : Dao<Set<UserId>> {
    fun hasBlocked(user: UserId, potentiallyBlockedUser: User) : Boolean
}