package dao

import model.Contact

interface ContactsOfUserDao : RelationsDao<UserId, Contact> {
    fun changeName(userId: UserId, contactId: Id, name: String)
}