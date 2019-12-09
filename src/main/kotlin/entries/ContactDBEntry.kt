package entries

import Contact
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import tables.Contacts

class ContactDBEntry(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<ContactDBEntry>(Contacts)

    var userId by Contacts.userId
    var contactId by Contacts.contactId
    var name by Contacts.name

    fun toContact() = Contact(id.value, userId.value, contactId.value, name)
}