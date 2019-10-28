package databases

import dao.ContactsOfUserDao
import dao.Id
import dao.UserId
import model.Contact

class ContactsOfUserDB : ContactsOfUserDao {
    private val base: MutableSet<Pair<UserId, Contact>> = mutableSetOf()

    override fun add(key: UserId, value: Contact): Boolean = base.add(key to value)
    override fun contains(key: UserId, value: Contact): Boolean = base.contains(key to value)
    override fun remove(key: UserId, value: Contact): Boolean = base.remove(key to value)
    override fun select(key: UserId): List<Contact> =
        base.mapNotNull { if (it.first == key) it.second else null }

    override fun changeName(userId: UserId, contactId: Id, name: String) {
        base.remove(userId to Contact(contactId, name))
        base.add(userId to Contact(contactId, name))
    }
}