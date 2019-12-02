package model

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import tables.Contacts

class Contact(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<Contact>(Contacts)

    var userId by Contacts.userId
    var contactId by Contacts.contactId
    var name by Contacts.name
}