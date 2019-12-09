package databases

import dao.Id
import dao.PersonalChatDao
import dao.PersonalChatId
import dao.UserId
import entries.PersonalChatDBEnrty
import entries.UserDBEntry
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.transactions.transaction
import tables.PersonalChats

class PersonalChatDB : PersonalChatDao {
    override fun addNewPersonalChat(member1: UserId, member2: UserId) =
        transaction {
            val id1 = UserDBEntry.findById(member1)?.id ?: return@transaction null
            val id2 = UserDBEntry.findById(member2)?.id ?: return@transaction null
            PersonalChatDBEnrty.new {
                this.member1 = id1
                this.member2 = id2
            }
        }

    override fun getById(elemId: Id) =
        transaction { PersonalChatDBEnrty.findById(elemId) }

    override fun deleteById(elemId: Id) =
        transaction { PersonalChatDBEnrty.findById(elemId)?.delete() ?: Unit }

    override val size: Int
        get() = transaction { PersonalChatDBEnrty.all().count() }

    override fun selectWithUser(user: UserId): List<PersonalChatId> =
        transaction {
            PersonalChatDBEnrty
                .find { (PersonalChats.member1 eq user) or (PersonalChats.member2 eq user) }
                .map { it.id.value }
        }
}