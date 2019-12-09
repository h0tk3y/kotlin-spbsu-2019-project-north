package databases

import dao.ContactsOfUserDao
import dao.Id
import dao.UserId
import entries.ContactDBEntry
import entries.UserDBEntry
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insertIgnoreAndGetId
import org.jetbrains.exposed.sql.transactions.transaction
import tables.Contacts
import tables.getEntityID

class ContactsOfUserDB : ContactsOfUserDao {
    override fun add(key: UserId, value: Pair<UserId, String>): Boolean =
        transaction {
            val keyId = getEntityID<UserDBEntry>(key) ?: return@transaction false
            val valueId = getEntityID<UserDBEntry>(value.first) ?: return@transaction false
            Contacts.insertIgnoreAndGetId {
                it[userId] = keyId
                it[contactId] = valueId
                it[name] = value.second
            } != null
        }

    override fun contains(key: UserId, value: Pair<UserId, String>): Boolean =
        transaction {
            val keyId = getEntityID<UserDBEntry>(key) ?: return@transaction false
            val valueId = getEntityID<UserDBEntry>(value.first) ?: return@transaction false
            !ContactDBEntry.find { (Contacts.userId eq keyId) and (Contacts.contactId eq valueId) }.empty()
        }

    override fun remove(key: UserId, value: Pair<UserId, String>): Boolean =
        transaction {
            val keyId = getEntityID<UserDBEntry>(key) ?: return@transaction false
            val valueId = getEntityID<UserDBEntry>(value.first) ?: return@transaction false
            ContactDBEntry.find { (Contacts.userId eq keyId) and (Contacts.contactId eq valueId) }
                .singleOrNull()?.delete() != null
        }

    override fun select(key: UserId): List<Pair<UserId, String>> =
        transaction {
            val keyId = getEntityID<UserDBEntry>(key) ?: return@transaction emptyList<Pair<UserId, String>>()
            ContactDBEntry.find { Contacts.userId eq keyId }.map { it.contactId.value to it.name }
        }

    override fun changeName(userId: UserId, contactId: Id, name: String) =
        transaction {
            val keyId = getEntityID<UserDBEntry>(userId) ?: return@transaction
            val valueId = getEntityID<UserDBEntry>(contactId) ?: return@transaction
            ContactDBEntry.find {
                (Contacts.userId eq keyId) and (Contacts.contactId eq valueId)
            }.singleOrNull()?.name = name
        }
}