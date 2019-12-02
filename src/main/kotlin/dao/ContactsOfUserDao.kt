package dao

interface ContactsOfUserDao : RelationsDao<UserId, Pair<UserId, String>> {
    fun changeName(userId: UserId, contactId: UserId, name: String)
}